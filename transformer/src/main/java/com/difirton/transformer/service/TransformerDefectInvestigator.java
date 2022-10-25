package com.difirton.transformer.service;

import com.difirton.transformer.db.entity.ChromatographicOilAnalysis;
import com.difirton.transformer.db.entity.PhysicalChemicalOilAnalysis;
import com.difirton.transformer.db.entity.Transformer;
import com.difirton.transformer.db.entity.TransformerStatus;
import com.difirton.transformer.service.constant.OilGas;
import com.difirton.transformer.service.constant.PhysicalChemicalOilParameter;
import com.difirton.transformer.service.constant.TypeDefect;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.difirton.transformer.service.constant.OilGas.*;

public class TransformerDefectInvestigator {
    private final Transformer transformer;
    private final OilStandardsComparator oilStandardsComparator;
    private List<ChromatographicOilAnalysis> chromatographicOilAnalyses;
    private List<PhysicalChemicalOilAnalysis> physicalChemicalOilAnalyses;
    private final int normalDaysBetweenOilSampling;
    private int recommendedDaysBetweenOilSampling;
    private final static double ONE_HUNDRED_TEN_PERCENT = 1.1;
    private final static int MULTIPLIER_LATEST_MEASUREMENTS = 50;
    private final static Map<OilGas, Double> LIMIT_DETECTION_DETERMINED_GASES = Map.of(H2, 5d,
                                                                                        CH4, 1d,
                                                                                        C2H4, 1d,
                                                                                        C2H6, 1d,
                                                                                        C2H2, 0.5,
                                                                                        CO, 20d,
                                                                                        CO2, 20d);

    public TransformerDefectInvestigator(Transformer transformer) {
        this.transformer = transformer;
        this.oilStandardsComparator = new OilStandardsComparator(transformer
                .getTransformerCharacteristics().getUpVoltage());
        if (transformer.getTransformerCharacteristics().getUpVoltage() < 150) {
            normalDaysBetweenOilSampling = 360;
        } else {
            normalDaysBetweenOilSampling = 180;
        }
    }

    public TransformerStatus checkTransformer() {
        if (transformer.getChromatographicOilAnalyses().isEmpty()) {
            return TransformerStatus.builder()
                    .transformer(transformer)
                    .defineDefect(TypeDefect.NORMAL)
                    .recommendedDaysBetweenOilSampling(normalDaysBetweenOilSampling)
                    .isDamagedPaperInsulation(false)
                    .build();
        }
        List<OilGas> gasesDetectedExcess = this.getGasesDetectedExcess();
        List<PhysicalChemicalOilParameter> oilParametersDetectedExcess =
                this.getPhysicalChemicalOilParametersDetectedExcess();
        if (transformer.getChromatographicOilAnalyses().size() > 1) {
            recommendedDaysBetweenOilSampling = (int) Math.ceil(this.getMinimumTimeToReselection());
            if (gasesDetectedExcess.size() > 1) {
                if (recommendedDaysBetweenOilSampling > normalDaysBetweenOilSampling) {
                    recommendedDaysBetweenOilSampling = normalDaysBetweenOilSampling;
                }
            }
            if (gasesDetectedExcess.isEmpty() && oilParametersDetectedExcess.isEmpty() &&
                    ONE_HUNDRED_TEN_PERCENT * recommendedDaysBetweenOilSampling > normalDaysBetweenOilSampling) {
                return TransformerStatus.builder().transformer(transformer)
                        .defineDefect(TypeDefect.NORMAL)
                        .recommendedDaysBetweenOilSampling(normalDaysBetweenOilSampling).build();
            } else {
                return this.checkAllDefects(gasesDetectedExcess, oilParametersDetectedExcess);
            }
        } else {
            return TransformerStatus.builder()
                    .transformer(transformer)
                    .gasesOutOfLimit(gasesDetectedExcess)
                    .defineOilParameterDefects(oilParametersDetectedExcess)
                    .defineDefect(TypeDefect.NORMAL)
                    .recommendedDaysBetweenOilSampling(normalDaysBetweenOilSampling)
                    .isDamagedPaperInsulation(false)
                    .build();
        }
    }

    private List<OilGas> getGasesDetectedExcess() {
        chromatographicOilAnalyses = transformer.getChromatographicOilAnalyses();
        ChromatographicOilAnalysis lastAnalysis = chromatographicOilAnalyses
                .get(chromatographicOilAnalyses.size() - 1);
        return oilStandardsComparator.compareWithStandardGasContent(lastAnalysis);
    }

    private List<PhysicalChemicalOilParameter> getPhysicalChemicalOilParametersDetectedExcess() {
        physicalChemicalOilAnalyses = transformer.getPhysicalChemicalOilAnalyses();
        if (physicalChemicalOilAnalyses.isEmpty()) {
            return Collections.emptyList();
        } else {
            PhysicalChemicalOilAnalysis lastAnalysis = physicalChemicalOilAnalyses
                    .get(physicalChemicalOilAnalyses.size() - 1);
            return oilStandardsComparator.compareWithStandardOilParameters(lastAnalysis);
        }
    }

    private double getMinimumTimeToReselection() {
        ChromatographicOilAnalysis lastAnalysis = chromatographicOilAnalyses.get(chromatographicOilAnalyses.size() - 1);
        ChromatographicOilAnalysis previousAnalysis = chromatographicOilAnalyses
                .get(chromatographicOilAnalyses.size() - 2);
        List<Double> daysToReselectionByEachGas = List.of(
                calculateDaysToReselection(lastAnalysis.getHydrogenGasH2(), lastAnalysis.getDateAnalysis(),
                        previousAnalysis.getHydrogenGasH2(), previousAnalysis.getDateAnalysis(), H2),
                calculateDaysToReselection(lastAnalysis.getMethaneCH4(), lastAnalysis.getDateAnalysis(),
                        previousAnalysis.getMethaneCH4(), previousAnalysis.getDateAnalysis(), CH4),
                calculateDaysToReselection(lastAnalysis.getEthyleneC2H4(), lastAnalysis.getDateAnalysis(),
                        previousAnalysis.getEthyleneC2H4(), previousAnalysis.getDateAnalysis(), C2H4),
                calculateDaysToReselection(lastAnalysis.getEthaneC2H6(), lastAnalysis.getDateAnalysis(),
                        previousAnalysis.getEthaneC2H6(), previousAnalysis.getDateAnalysis(), C2H6));
        return daysToReselectionByEachGas.stream().min(Double::compareTo).get();
    }

    private double calculateDaysToReselection(Integer lastValue, LocalDate dateLastAnalysis,
                                        Integer previousValue, LocalDate datePreviousAnalysis, OilGas typeGas) {
        double countDaysPeriod = (double) ChronoUnit.DAYS.between(datePreviousAnalysis, dateLastAnalysis);
        if (countDaysPeriod == 0) {
            countDaysPeriod = 1;
        }
        double gasSlewRate  = Math.abs(lastValue - previousValue) * 30 / countDaysPeriod;
        return MULTIPLIER_LATEST_MEASUREMENTS * LIMIT_DETECTION_DETERMINED_GASES.get(typeGas) / gasSlewRate;
    }

    private TransformerStatus checkAllDefects(List<OilGas> gasesDetectedExcess,
                                              List<PhysicalChemicalOilParameter> oilParametersDetectedExcess) {
        TransformerStatus transformerStatus = TransformerStatus.builder().transformer(transformer).build();
        if (!gasesDetectedExcess.isEmpty()) {
            transformerStatus.setGasesOutOfLimit(gasesDetectedExcess);
        }
        if (!oilParametersDetectedExcess.isEmpty()) {
            transformerStatus.setDefineOilParameterDefects(oilParametersDetectedExcess);
        }
        ChromatographicOilAnalysis lastAnalysis = chromatographicOilAnalyses.get(chromatographicOilAnalyses.size() - 1);
        TypeDefect defect = new GasDefectFinder(lastAnalysis).detectTypeDefect();
        transformerStatus.setDefineDefect(defect);
        if (defect == TypeDefect.NORMAL) {
            transformerStatus.setIsDamagedPaperInsulation(false);
        } else {
            transformerStatus.setIsDamagedPaperInsulation(this.isDamagedPaperInsulation(lastAnalysis));
        }
        transformerStatus.setRecommendedDaysBetweenOilSampling(recommendedDaysBetweenOilSampling);
        return transformerStatus;
    }

    private boolean isDamagedPaperInsulation(ChromatographicOilAnalysis analysis) {
        Integer carbonMonoxideCO = analysis.getCarbonMonoxideCO();
        Integer carbonDioxideCO2 = analysis.getCarbonDioxideCO2();
        return (carbonDioxideCO2 / carbonMonoxideCO < 5) || (carbonDioxideCO2 / carbonMonoxideCO > 13);
    }
}
