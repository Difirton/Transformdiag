package com.difirton.transformdiag.service;

import com.difirton.transformdiag.db.entity.ChromatographicOilAnalysis;
import com.difirton.transformdiag.db.entity.PhysicalChemicalOilAnalysis;
import com.difirton.transformdiag.db.entity.Transformer;
import com.difirton.transformdiag.service.constant.OilGas;
import com.difirton.transformdiag.service.constant.PhysicalChemicalOilParameter;
import com.difirton.transformdiag.service.constant.TypeDefect;
import com.difirton.transformdiag.service.dto.TransformerDefectDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TransformerDefectInvestigator {
    private final Transformer transformer;
    private final OilStandardsComparator oilStandardsComparator;
    private List<ChromatographicOilAnalysis> chromatographicOilAnalyses;
    private List<PhysicalChemicalOilAnalysis> physicalChemicalOilAnalyses;
    private final int normalDaysBetweenOilSampling;
    private int recommendedDaysBetweenOilSampling;
    private final static double ONE_HUNDRED_TEN_PERCENT = 1.1;

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

    public Optional<TransformerDefectDto> checkTransformer() {
        List<OilGas> gasesDetectedExcess = this.getGasesDetectedExcess();
        List<PhysicalChemicalOilParameter> oilParametersDetectedExcess =
                this.getPhysicalChemicalOilParametersDetectedExcess();
        if (gasesDetectedExcess.size() > 1) {
            recommendedDaysBetweenOilSampling = (int) Math.ceil(this.getMinimumTimeToReselection());
        } else {
            recommendedDaysBetweenOilSampling = normalDaysBetweenOilSampling;
        }
        if (gasesDetectedExcess.isEmpty() && oilParametersDetectedExcess.isEmpty() &&
                ONE_HUNDRED_TEN_PERCENT * recommendedDaysBetweenOilSampling > normalDaysBetweenOilSampling) {
            return Optional.empty();
        } else {
            return Optional.of(this.checkAllDefects(gasesDetectedExcess, oilParametersDetectedExcess));
        }
    }

    private List<OilGas> getGasesDetectedExcess() {
        chromatographicOilAnalyses = transformer.getChromatographicOilAnalyses();
        if (chromatographicOilAnalyses.isEmpty()) {
            return Collections.emptyList();
        } else {
            ChromatographicOilAnalysis lastAnalysis = chromatographicOilAnalyses
                    .get(chromatographicOilAnalyses.size() - 1);
            return oilStandardsComparator.compareWithStandardGasContent(lastAnalysis);
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
        List<Double> result = List.of(
                calculateDaysToReselection(lastAnalysis.getHydrogenGasH2(), previousAnalysis.getHydrogenGasH2()),
                calculateDaysToReselection(lastAnalysis.getMethaneCH4(), previousAnalysis.getMethaneCH4()),
                calculateDaysToReselection(lastAnalysis.getEthyleneC2H4(), previousAnalysis.getEthyleneC2H4()),
                calculateDaysToReselection(lastAnalysis.getEthaneC2H6(), previousAnalysis.getEthaneC2H6()));
        return result.stream().min(Double::compareTo).get();
    }

    private double calculateDaysToReselection(Integer last, Integer previous) {
        return 25 * 0.0001 / (((last - previous) * 30) / 6);
    }

    private TransformerDefectDto checkAllDefects(List<OilGas> gasesDetectedExcess,
                                                 List<PhysicalChemicalOilParameter> oilParametersDetectedExcess) {
        TransformerDefectDto transformerDefectDto = new TransformerDefectDto(transformer);
        if (!gasesDetectedExcess.isEmpty()) {
            transformerDefectDto.getGasesOutOfLimit().addAll(gasesDetectedExcess);
        }
        if (!oilParametersDetectedExcess.isEmpty()) {
            transformerDefectDto.getDefineOilParameterDefects().addAll(oilParametersDetectedExcess);
        }
        ChromatographicOilAnalysis lastAnalysis = chromatographicOilAnalyses.get(chromatographicOilAnalyses.size() - 1);
        TypeDefect defect = new GasDefectFinder(lastAnalysis).detectTypeDefect();
        transformerDefectDto.setDefineDefect(defect);
        transformerDefectDto.setDamagedPaperInsulation(this.isDamagedPaperInsulation(lastAnalysis));
        transformerDefectDto.setRecommendedDaysBetweenOilSampling(recommendedDaysBetweenOilSampling);
        return transformerDefectDto;
    }

    private boolean isDamagedPaperInsulation(ChromatographicOilAnalysis analysis) {
        Integer carbonMonoxideCO = analysis.getCarbonMonoxideCO();
        Integer carbonDioxideCO2 = analysis.getCarbonDioxideCO2();
        return (carbonDioxideCO2 / carbonMonoxideCO < 5) || (carbonDioxideCO2 / carbonMonoxideCO > 13);
    }
}
