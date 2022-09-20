package com.difirton.transformdiag.service;

import com.difirton.transformdiag.db.entity.ChromatographicOilAnalysis;
import com.difirton.transformdiag.db.entity.PhysicalChemicalOilAnalysis;
import com.difirton.transformdiag.db.entity.Transformer;
import com.difirton.transformdiag.db.entity.TransformerStatus;
import com.difirton.transformdiag.error.EmptyListOfAnalysisException;
import com.difirton.transformdiag.service.constant.OilGas;
import com.difirton.transformdiag.service.constant.PhysicalChemicalOilParameter;
import com.difirton.transformdiag.service.constant.TypeDefect;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.difirton.transformdiag.service.constant.OilGas.*;

public class TransformerDefectInvestigator {
    private final Transformer transformer;
    private final OilStandardsComparator oilStandardsComparator;
    private List<ChromatographicOilAnalysis> chromatographicOilAnalyses;
    private List<PhysicalChemicalOilAnalysis> physicalChemicalOilAnalyses;
    private final int normalDaysBetweenOilSampling;
    private int recommendedDaysBetweenOilSampling;
    private final static double ONE_HUNDRED_TEN_PERCENT = 1.1;
    private final static int MULTIPLIER_LATEST_MEASUREMENTS = 5;
    private final static Map<OilGas, Double> LIMIT_DETECTION_DETERMINED_GASES = Map.of(H2, 0.0005,
                                                                                        CH4, 0.0001,
                                                                                        C2H4, 0.0001,
                                                                                        C2H6, 0.0001,
                                                                                        C2H2, 0.00005,
                                                                                        CO, 0.002,
                                                                                        CO2, 0.002);

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
        List<OilGas> gasesDetectedExcess = this.getGasesDetectedExcess();
        List<PhysicalChemicalOilParameter> oilParametersDetectedExcess =
                this.getPhysicalChemicalOilParametersDetectedExcess();
        if (gasesDetectedExcess.size() > 0) {
            recommendedDaysBetweenOilSampling = (int) Math.ceil(this.getMinimumTimeToReselection());
        } else {
            recommendedDaysBetweenOilSampling = normalDaysBetweenOilSampling;
        }
        if (gasesDetectedExcess.isEmpty() && oilParametersDetectedExcess.isEmpty() &&
                ONE_HUNDRED_TEN_PERCENT * recommendedDaysBetweenOilSampling > normalDaysBetweenOilSampling) {
            return TransformerStatus.builder().transformer(transformer)
                    .defineDefect(TypeDefect.NORMAL)
                    .recommendedDaysBetweenOilSampling(normalDaysBetweenOilSampling).build();
        } else {
            return this.checkAllDefects(gasesDetectedExcess, oilParametersDetectedExcess);
        }
    }

    private List<OilGas> getGasesDetectedExcess() {
        chromatographicOilAnalyses = transformer.getChromatographicOilAnalyses();
        if (chromatographicOilAnalyses.size() > 1) {
            ChromatographicOilAnalysis lastAnalysis = chromatographicOilAnalyses
                    .get(chromatographicOilAnalyses.size() - 1);
            return oilStandardsComparator.compareWithStandardGasContent(lastAnalysis);
        } else {
            throw new EmptyListOfAnalysisException(transformer.getId());
        }
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
        double gasSlewRate  = (lastValue - previousValue) * 30 /
                Period.between(datePreviousAnalysis, dateLastAnalysis).getDays();
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
        transformerStatus.setIsDamagedPaperInsulation(this.isDamagedPaperInsulation(lastAnalysis));
        transformerStatus.setRecommendedDaysBetweenOilSampling(recommendedDaysBetweenOilSampling);
        return transformerStatus;
    }

    private boolean isDamagedPaperInsulation(ChromatographicOilAnalysis analysis) {
        Integer carbonMonoxideCO = analysis.getCarbonMonoxideCO();
        Integer carbonDioxideCO2 = analysis.getCarbonDioxideCO2();
        return (carbonDioxideCO2 / carbonMonoxideCO < 5) || (carbonDioxideCO2 / carbonMonoxideCO > 13);
    }
}
