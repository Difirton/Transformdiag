package com.difirton.transformdiag.service;

import com.difirton.transformdiag.db.entity.ChromatographicOilAnalysis;
import com.difirton.transformdiag.service.constant.TypeDefect;

import java.util.*;
import java.util.stream.Collectors;

import static com.difirton.transformdiag.service.constant.TypeDefect.*;

public class GasDefectFinder {
    private static final int NUMBER_COLUMN_C2H2_C2H4 = 0;
    private static final int NUMBER_COLUMN_CH4_H2 = 1;
    private static final int NUMBER_COLUMN_C2H4_C2H6 = 2;
    private final ChromatographicOilAnalysis chromatographicOilAnalysis;

    public GasDefectFinder(ChromatographicOilAnalysis chromatographicOilAnalysis) {
        this.chromatographicOilAnalysis = chromatographicOilAnalysis;
    }

    public TypeDefect detectTypeDefect() {
        List<Double> gasRatios = this.defineGasRations(chromatographicOilAnalysis);
        return this.defineDefect(gasRatios);
    }

    private List<Double> defineGasRations(ChromatographicOilAnalysis analysis) {
        List<Double> gasRatios = new ArrayList<>();
        Integer C2H2 = analysis.getAcetyleneC2H2();
        Integer C2H4 = analysis.getEthyleneC2H4();
        Integer CH4 = analysis.getMethaneCH4();
        Integer H2 = analysis.getHydrogenGasH2();
        Integer C2H6 = analysis.getEthaneC2H6();
        gasRatios.add(NUMBER_COLUMN_C2H2_C2H4, (double) C2H2 / C2H4);
        gasRatios.add(NUMBER_COLUMN_CH4_H2, (double) CH4 / H2);
        gasRatios.add(NUMBER_COLUMN_C2H4_C2H6, (double) C2H4 / C2H6);
        return gasRatios;
    }

    private TypeDefect defineDefect(List<Double> gasRatios) {
        Map<TypeDefect, Integer> resultFirstArgsRelation = defineFirstArgsRelation(gasRatios);
        Map<TypeDefect, Integer> resultSecondArgsRelation = defineSecondArgsRelation(gasRatios, resultFirstArgsRelation);
        Map<TypeDefect, Integer> finalResultArgsRelation = defineThirdArgsRelation(gasRatios, resultSecondArgsRelation);
        Integer numberMatches = Collections.max(finalResultArgsRelation.values());
        if (numberMatches > 2) {
            return finalResultArgsRelation.entrySet().stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .get();
        } else if (numberMatches > 1) {
            List<TypeDefect> actualDefects = finalResultArgsRelation.entrySet().stream()
                    .filter(o -> Objects.equals(o.getValue(), numberMatches))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            return this.difficultCaseTypeDefine(actualDefects, gasRatios);
        } else {
            return NORMAL;
        }
    }

    private Map<TypeDefect, Integer> defineFirstArgsRelation(List<Double> gasRatios) {
        Map<TypeDefect, Integer> resultFirstArgsRelation = new HashMap<>();
        if (gasRatios.get(NUMBER_COLUMN_C2H2_C2H4) < 0.12) {
            resultFirstArgsRelation.put(NORMAL, 1);
            resultFirstArgsRelation.put(PD_LOW_DENSITY, 1);
            resultFirstArgsRelation.put(DISCHARGE_LOW_POWER, 1);
            resultFirstArgsRelation.put(THERMAL_DEFECT_LOW, 1);
            resultFirstArgsRelation.put(THERMAL_DEFECT_MEDIUM, 1);
            resultFirstArgsRelation.put(THERMAL_DEFECT_HIGH, 1);
            resultFirstArgsRelation.put(THERMAL_DEFECT_VERY_HIGH, 1);
        }
        if (gasRatios.get(NUMBER_COLUMN_C2H2_C2H4) > 0.08 && gasRatios.get(NUMBER_COLUMN_C2H2_C2H4) < 3.6) {
            resultFirstArgsRelation.put(PD_HIGH_DENSITY, 1);
            resultFirstArgsRelation.put(DISCHARGE_HIGH_POWER, 1);
        }
        return resultFirstArgsRelation;
    }

    private Map<TypeDefect, Integer> defineSecondArgsRelation(List<Double> gasRatios, Map<TypeDefect, Integer> result) {
        if (gasRatios.get(NUMBER_COLUMN_CH4_H2) < 0.12) {
            result.put(PD_LOW_DENSITY, result.getOrDefault(PD_LOW_DENSITY, 0) + 1);
            result.put(PD_HIGH_DENSITY, result.getOrDefault(PD_HIGH_DENSITY, 0) + 1);
        }
        if (gasRatios.get(NUMBER_COLUMN_CH4_H2) > 0.08 && gasRatios.get(NUMBER_COLUMN_CH4_H2) < 1.2 ) {
            result.put(NORMAL, result.getOrDefault(NORMAL, 0) + 1);
            result.put(DISCHARGE_LOW_POWER, result.getOrDefault(DISCHARGE_LOW_POWER, 0) + 1);
            result.put(DISCHARGE_HIGH_POWER, result.getOrDefault(DISCHARGE_HIGH_POWER, 0) + 1);
            result.put(THERMAL_DEFECT_LOW, result.getOrDefault(THERMAL_DEFECT_LOW, 0) + 1);
        }
        if (gasRatios.get(NUMBER_COLUMN_CH4_H2) > 0.8) {
            result.put(THERMAL_DEFECT_MEDIUM, result.getOrDefault(THERMAL_DEFECT_MEDIUM, 0) + 1);
            result.put(THERMAL_DEFECT_HIGH, result.getOrDefault(THERMAL_DEFECT_HIGH, 0) + 1);
            result.put(THERMAL_DEFECT_VERY_HIGH, result.getOrDefault(THERMAL_DEFECT_VERY_HIGH, 0) + 1);
        }
        return result;
    }

    private Map<TypeDefect, Integer> defineThirdArgsRelation(List<Double> gasRatios, Map<TypeDefect, Integer> result) {
        if (gasRatios.get(NUMBER_COLUMN_C2H4_C2H6) < 1.2) {
            result.put(NORMAL, result.getOrDefault(NORMAL, 0) + 1);
            result.put(PD_LOW_DENSITY, result.getOrDefault(PD_LOW_DENSITY, 0) + 1);
            result.put(PD_HIGH_DENSITY, result.getOrDefault(PD_HIGH_DENSITY, 0) + 1);
            result.put(THERMAL_DEFECT_MEDIUM, result.getOrDefault(THERMAL_DEFECT_MEDIUM, 0) + 1);
        }
        if (gasRatios.get(NUMBER_COLUMN_C2H4_C2H6) > 0.8 && gasRatios.get(NUMBER_COLUMN_C2H4_C2H6) < 3.6) {
            result.put(DISCHARGE_LOW_POWER, result.getOrDefault(DISCHARGE_LOW_POWER, 0) + 1);
            result.put(THERMAL_DEFECT_LOW, result.getOrDefault(THERMAL_DEFECT_LOW, 0) + 1);
            result.put(THERMAL_DEFECT_MEDIUM, result.getOrDefault(THERMAL_DEFECT_HIGH, 0) + 1);
        }
        if (gasRatios.get(NUMBER_COLUMN_C2H4_C2H6) > 3.6) {
            result.put(DISCHARGE_HIGH_POWER, result.getOrDefault(DISCHARGE_HIGH_POWER, 0) + 1);
            result.put(THERMAL_DEFECT_VERY_HIGH, result.getOrDefault(THERMAL_DEFECT_VERY_HIGH, 0) + 1);
        }
        return result;
    }

    private TypeDefect difficultCaseTypeDefine(List<TypeDefect> typeDefects, List<Double> gasRatios) {
        Map<TypeDefect, Double> result = new HashMap<>();
        for (TypeDefect typeDefect : typeDefects) {
            result.put(typeDefect, this.calculateProbability(typeDefect, gasRatios));
        }
        return result.entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .get();
    }

    private Double calculateProbability(TypeDefect typeDefect, List<Double> gasRatios) {
        return switch (typeDefect) {
            case NORMAL:
                yield calculateProbabilityNormal(gasRatios);
            case PD_LOW_DENSITY:
                yield calculateProbabilityPdLowDensity(gasRatios);
            case PD_HIGH_DENSITY:
                yield calculateProbabilityPdHighDensity(gasRatios);
            case DISCHARGE_LOW_POWER:
                yield calculateProbabilityDischargeLowPower(gasRatios);
            case DISCHARGE_HIGH_POWER:
                yield calculateProbabilityDischargeHighPower(gasRatios);
            case THERMAL_DEFECT_LOW:
                yield calculateProbabilityThermalDefectLow(gasRatios);
            case THERMAL_DEFECT_MEDIUM:
                yield calculateProbabilityThermalDefectMedium(gasRatios);
            case THERMAL_DEFECT_HIGH:
                yield calculateProbabilityThermalDefectHigh(gasRatios);
            case THERMAL_DEFECT_VERY_HIGH:
                yield calculateProbabilityThermalDefectVeryHigh(gasRatios);
        };
    }

    private Double calculateProbabilityNormal(List<Double> gasRatios) {
        return Math.abs(gasRatios.get(NUMBER_COLUMN_C2H2_C2H4) - 0.05) / 0.05 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 0.55) / 0.55 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 0.5) / 0.5;
    }

    private Double calculateProbabilityPdLowDensity(List<Double> gasRatios) {
        return Math.abs(gasRatios.get(NUMBER_COLUMN_C2H2_C2H4) - 0.05) / 0.05 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 0.05) / 0.05 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 0.5) / 0.5;
    }

    private Double calculateProbabilityPdHighDensity(List<Double> gasRatios) {
        return Math.abs(gasRatios.get(NUMBER_COLUMN_C2H2_C2H4) - 1.45) / 1.45 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 0.05) / 0.05 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 0.5) / 0.5;
    }

    private Double calculateProbabilityDischargeLowPower(List<Double> gasRatios) {
        return Math.abs(gasRatios.get(NUMBER_COLUMN_C2H2_C2H4) - 0.05) / 0.05 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 0.55) / 0.55 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 2) / 2;
    }

    private Double calculateProbabilityDischargeHighPower(List<Double> gasRatios) {
        return Math.abs(gasRatios.get(NUMBER_COLUMN_C2H2_C2H4) - 1.45) / 1.45 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 0.55) / 0.55 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 6) / 6;
    }

    private Double calculateProbabilityThermalDefectLow(List<Double> gasRatios) {
        return Math.abs(gasRatios.get(NUMBER_COLUMN_C2H2_C2H4) - 0.05) / 0.05 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 0.55) / 0.55 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 2) / 2;
    }

    private Double calculateProbabilityThermalDefectMedium(List<Double> gasRatios) {
        return Math.abs(gasRatios.get(NUMBER_COLUMN_C2H2_C2H4) - 0.05) / 0.05 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 1.55) / 1.55 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 0.5) / 0.5;
    }

    private Double calculateProbabilityThermalDefectHigh(List<Double> gasRatios) {
        return Math.abs(gasRatios.get(NUMBER_COLUMN_C2H2_C2H4) - 0.05) / 0.05 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 1.55) / 1.55 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 2) / 2;
    }

    private Double calculateProbabilityThermalDefectVeryHigh(List<Double> gasRatios) {
        return Math.abs(gasRatios.get(NUMBER_COLUMN_C2H2_C2H4) - 0.05) / 0.05 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 1.55) / 1.55 +
                Math.abs(gasRatios.get(NUMBER_COLUMN_CH4_H2) - 6) / 6;
    }
}
