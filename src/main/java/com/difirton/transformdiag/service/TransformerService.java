package com.difirton.transformdiag.service;

import com.difirton.transformdiag.db.entity.PhysicalChemicalOilAnalysis;
import com.difirton.transformdiag.db.entity.Transformer;
import com.difirton.transformdiag.db.entity.TransformerCharacteristics;
import com.difirton.transformdiag.db.entity.TransformerStatus;
import com.difirton.transformdiag.db.repository.*;
import com.difirton.transformdiag.error.EmptyListOfAnalysisException;
import com.difirton.transformdiag.error.TransformerNotFoundException;
import com.difirton.transformdiag.service.constant.OilGas;
import com.difirton.transformdiag.service.constant.PhysicalChemicalOilParameter;
import com.difirton.transformdiag.service.constant.TypeDefect;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TransformerService {
    private TransformerRepository transformerRepository;
    private TransformerCharacteristicsRepository transformerCharacteristicsRepository;
    private TransformerStatusRepository transformerStatusRepository;
    private ChromatographicOilAnalysisRepository chromatographicOilAnalysisRepository;
    private PhysicalChemicalOilAnalysisRepository physicalChemicalOilAnalysisRepository;

    @Autowired
    public TransformerService(TransformerRepository transformerRepository,
                              TransformerCharacteristicsRepository transformerCharacteristicsRepository,
                              TransformerStatusRepository transformerStatusRepository,
                              ChromatographicOilAnalysisRepository chromatographicOilAnalysisRepository,
                              PhysicalChemicalOilAnalysisRepository physicalChemicalOilAnalysisRepository) {
        this.transformerRepository = transformerRepository;
        this.transformerCharacteristicsRepository = transformerCharacteristicsRepository;
        this.transformerStatusRepository = transformerStatusRepository;
        this.chromatographicOilAnalysisRepository = chromatographicOilAnalysisRepository;
        this.physicalChemicalOilAnalysisRepository = physicalChemicalOilAnalysisRepository;
    }

    public List<Transformer> getAllTransformer() {
        return transformerRepository.findAll();
    }

    @Transactional
    public Transformer saveTransformer(Transformer transformer, TransformerCharacteristics characteristics) {
        characteristics.setTransformer(transformer);
        transformerCharacteristicsRepository.save(characteristics);
        return transformerRepository.save(transformer);
    }

    public Transformer getTransformerById(Long id) {
        return transformerRepository.findById(id).orElseThrow(() -> new TransformerNotFoundException(id));
    }

    public Transformer updateTransformer(Long id, Transformer transformer) {
        return transformerRepository.findById(id)
                .map(t -> {
                    t.setKKS(transformer.getKKS());
                    t.setType(transformer.getType());
                    t.setFactoryNumber(transformer.getFactoryNumber());
                    return transformerRepository.save(t);
                })
                .orElseThrow(() -> new TransformerNotFoundException(id));
    }

    public TransformerCharacteristics getTransformerCharacteristicsById(Long id) {
        return transformerCharacteristicsRepository.findTransformerCharacteristicsByTransformerId(id);
    }

    public void deleteTransformerById(Long id) {
        transformerRepository.deleteById(id);
    }

    @Transactional
    public Transformer saveTransformer(Transformer transformer) {
        transformerCharacteristicsRepository.save(transformer.getTransformerCharacteristics());
        return transformerRepository.save(transformer);
    }

    public TransformerStatus getCurrentTransformStatus(Long transformerId) {
        return transformerStatusRepository.findTransformerStatusByTransformerId(transformerId)
                .orElse(getNewTransformStatus(transformerId));
    }

    @Transactional
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

    @Transactional
    public String getReportOfTransformDefects(Long transformerId) {
        TransformerDefectInvestigator transformerDefectInvestigator =
                new TransformerDefectInvestigator(transformerRepository.findById(transformerId)
                        .orElseThrow(() -> new TransformerNotFoundException(transformerId)));
        TransformerStatus report = transformerDefectInvestigator.checkTransformer();
        return this.generateReport(report);
    }

    private String generateReport(TransformerStatus transformerStatus) {
        StringBuilder sb = new StringBuilder();
        if (!transformerStatus.getGasesOutOfLimit().isEmpty()) {
            sb.append("It is necessary to degas the oil in the transformer, an excess of the normal gas content in " +
                    "gases was revealed: ");
            for (OilGas oilGas : transformerStatus.getGasesOutOfLimit()) {
                sb.append(oilGas);
                sb.append(", ");
            }
        }
        if (!transformerStatus.getDefineOilParameterDefects().isEmpty()) {
            sb.append("\n It is necessary to replace or treat transformer oil, inappropriate indicators: ");
            for (PhysicalChemicalOilParameter oilParameter : transformerStatus.getDefineOilParameterDefects()) {
                sb.append(oilParameter);
                sb.append(", ");
            }
        }
        if (transformerStatus.getDefineDefect() != TypeDefect.NORMAL) {
            sb.append("\n Important! As a result of gas analysis, a defect was detected: ");
            sb.append(transformerStatus.getDefineDefect());
            if (transformerStatus.getIsDamagedPaperInsulation()) {
                sb.append("\n Paper insulation hurt");
            }
            sb.append("\n The re-extraction of oil must be carried out after ");
            sb.append(transformerStatus.getRecommendedDaysBetweenOilSampling());
        }
        return sb.toString();
    }
}
