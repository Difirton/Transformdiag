package com.difirton.investigator.service;

import com.difirton.investigator.db.entity.Transformer;
import com.difirton.investigator.db.entity.TransformerStatus;
import com.difirton.investigator.db.repository.TransformerRepository;
import com.difirton.investigator.error.TransformerNotFoundException;
import com.difirton.investigator.service.constant.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransformerInvestigatorServiceImpl implements InvestigatorService {
    private TransformerRepository transformerRepository;

    @Autowired
    public TransformerInvestigatorServiceImpl(TransformerRepository transformerRepository) {
        this.transformerRepository = transformerRepository;
    }

    @Override
    public TransformerStatus getNewTransformStatus(Long transformerId) {
        TransformerStatus transformerStatus;
        Transformer transformer = transformerRepository.findById(transformerId)
                .orElseThrow(() -> new TransformerNotFoundException(transformerId));
        transformer.setChromatographicOilAnalyses(chromatographicOilAnalysisRepository
                .findByTransformerId(transformerId));
        transformer.setPhysicalChemicalOilAnalyses(physicalChemicalOilAnalysisRepository
                .findByTransformerId(transformerId));
        if (transformer.getChromatographicOilAnalyses().size() > 1){
            TransformerDefectInvestigator transformerDefectInvestigator = new TransformerDefectInvestigator(transformer);
            transformerStatus = transformerDefectInvestigator.checkTransformer();
        } else {
            transformerStatus = TransformerStatus.builder()
                    .transformer(transformer)
                    .defineDefect(TypeDefect.NORMAL)
                    .isDamagedPaperInsulation(false)
                    .recommendedDaysBetweenOilSampling(60)
                    .build();
        }
        return transformerStatusRepository.save(transformerStatus);
    }

    @Override
    public String getReportOfTransformDefects(Long transformerId) {
        TransformerDefectInvestigator transformerDefectInvestigator =
                new TransformerDefectFinder(transformerRepository.findById(transformerId)
                        .orElseThrow(() -> new TransformerNotFoundException(transformerId)));
        TransformerStatus report = transformerDefectInvestigator.checkTransformer();
        return this.generateReport(report);
    }


    public TransformerStatus getCurrentTransformStatus(Long transformerId) {
        return transformerStatusRepository.findTransformerStatusByTransformerId(transformerId)
                .orElse(getNewTransformStatus(transformerId));
    }

    //    private String generateReport(TransformerStatus transformerStatus) {
//        StringBuilder sb = new StringBuilder();
//        if (!transformerStatus.getGasesOutOfLimit().isEmpty()) {
//            sb.append("It is necessary to degas the oil in the transformer, an excess of the normal gas content in " +
//                    "gases was revealed: ");
//            for (OilGas oilGas : transformerStatus.getGasesOutOfLimit()) {
//                sb.append(oilGas);
//                sb.append(", ");
//            }
//        }
//        if (!transformerStatus.getDefineOilParameterDefects().isEmpty()) {
//            sb.append("\n It is necessary to replace or treat transformer oil, inappropriate indicators: ");
//            for (PhysicalChemicalOilParameter oilParameter : transformerStatus.getDefineOilParameterDefects()) {
//                sb.append(oilParameter);
//                sb.append(", ");
//            }
//        }
//        if (transformerStatus.getDefineDefect() != TypeDefect.NORMAL) {
//            sb.append("\n Important! As a result of gas analysis, a defect was detected: ");
//            sb.append(transformerStatus.getDefineDefect());
//            if (transformerStatus.getIsDamagedPaperInsulation()) {
//                sb.append("\n Paper insulation hurt");
//            }
//            sb.append("\n The re-extraction of oil must be carried out after ");
//            sb.append(transformerStatus.getRecommendedDaysBetweenOilSampling());
//        }
//        return sb.toString();
//    }
}