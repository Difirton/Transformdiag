package com.difirton.transformdiag.service;

import com.difirton.transformdiag.db.entity.PhysicalChemicalOilAnalysis;
import com.difirton.transformdiag.db.repository.PhysicalChemicalOilAnalysisRepository;
import com.difirton.transformdiag.db.repository.TransformerRepository;
import com.difirton.transformdiag.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PhysicalChemicalOilAnalysisService {
    PhysicalChemicalOilAnalysisRepository physicalChemicalOilAnalysisRepository;
    TransformerRepository transformerRepository;

    @Autowired
    public PhysicalChemicalOilAnalysisService(PhysicalChemicalOilAnalysisRepository physicalChemicalOilAnalysisRepository,
                                              TransformerRepository transformerRepository) {
        this.physicalChemicalOilAnalysisRepository = physicalChemicalOilAnalysisRepository;
        this.transformerRepository = transformerRepository;
    }

    public List<PhysicalChemicalOilAnalysis> getAllPhysicalChemicalOilAnalysisByTransformerId(Long transformerId) {
        return physicalChemicalOilAnalysisRepository.findByTransformerId(transformerId);
    }

    public void createPhysicalChemicalOilAnalysis(PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis,
                                                  Long transformerId, String dateAnalysis) {
        physicalChemicalOilAnalysis.setDateAnalysis(DateUtil.stringToDate(dateAnalysis));
        physicalChemicalOilAnalysis.setTransformer(transformerRepository.findById(transformerId).get());
        physicalChemicalOilAnalysisRepository.save(physicalChemicalOilAnalysis);
    }

    public PhysicalChemicalOilAnalysis getPhysicalChemicalOilAnalysisById(Long id) {
        return physicalChemicalOilAnalysisRepository.findById(id).get();
    }

    public void updatePhysicalChemicalOilAnalysis(PhysicalChemicalOilAnalysis modifiedAnalysis) {
        PhysicalChemicalOilAnalysis oldAnalysis = physicalChemicalOilAnalysisRepository
                .findById(modifiedAnalysis.getId()).get();
        if (modifiedAnalysis.getDateAnalysis() != null ||
                !modifiedAnalysis.getDateAnalysis().equals(oldAnalysis.getDateAnalysis())) {
            oldAnalysis.setDateAnalysis(modifiedAnalysis.getDateAnalysis());
        }
        if (modifiedAnalysis.getFlashPoint() != null ||
                !modifiedAnalysis.getFlashPoint().equals(oldAnalysis.getFlashPoint())) {
            oldAnalysis.setFlashPoint(modifiedAnalysis.getFlashPoint());
        }
        if (modifiedAnalysis.getAcidNumber() != null ||
                !modifiedAnalysis.getAcidNumber().equals(oldAnalysis.getAcidNumber())) {
            oldAnalysis.setAcidNumber(modifiedAnalysis.getAcidNumber());
        }
        if (modifiedAnalysis.getCleanlinessClass() != null ||
                !modifiedAnalysis.getCleanlinessClass().equals(oldAnalysis.getCleanlinessClass())) {
            oldAnalysis.setCleanlinessClass(modifiedAnalysis.getCleanlinessClass());
        }
        if (modifiedAnalysis.getMoistureContent() != null ||
                !modifiedAnalysis.getMoistureContent().equals(oldAnalysis.getMoistureContent())) {
            oldAnalysis.setMoistureContent(modifiedAnalysis.getMoistureContent());
        }
        if (modifiedAnalysis.getBreakdownVoltage() != null ||
                !modifiedAnalysis.getBreakdownVoltage().equals(oldAnalysis.getBreakdownVoltage())) {
            oldAnalysis.setBreakdownVoltage(modifiedAnalysis.getBreakdownVoltage());
        }
        if (modifiedAnalysis.getDielectricLossTangent() != null ||
                !modifiedAnalysis.getDielectricLossTangent().equals(oldAnalysis.getDielectricLossTangent())) {
            oldAnalysis.setDielectricLossTangent(modifiedAnalysis.getDielectricLossTangent());
        }
        if (modifiedAnalysis.getProtocolName() != null ||
                !modifiedAnalysis.getProtocolName().equals(oldAnalysis.getProtocolName())) {
            oldAnalysis.setProtocolName(modifiedAnalysis.getProtocolName());
        }
        physicalChemicalOilAnalysisRepository.save(oldAnalysis);
    }

    public void deletePhysicalChemicalOilAnalysisById(Long id) {
        physicalChemicalOilAnalysisRepository.deleteById(id);
    }
}
