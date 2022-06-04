package com.difirton.transformdiag.service;

import com.difirton.transformdiag.entitys.PhysicalChemicalOilAnalysis;
import com.difirton.transformdiag.repository.PhysicalChemicalOilAnalysisRepository;
import com.difirton.transformdiag.repository.TransformerRepository;
import com.difirton.transformdiag.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhysicalChemicalOilAnalysisService {
    PhysicalChemicalOilAnalysisRepository physicalChemicalOilAnalysisRepository;
    TransformerRepository transformerRepository;

    @Autowired
    public PhysicalChemicalOilAnalysisService(PhysicalChemicalOilAnalysisRepository physicalChemicalOilAnalysisRepository, TransformerRepository transformerRepository) {
        this.physicalChemicalOilAnalysisRepository = physicalChemicalOilAnalysisRepository;
        this.transformerRepository = transformerRepository;
    }

    public List<PhysicalChemicalOilAnalysis> getAllPhysicalChemicalOilAnalysisByTransformerId(Long transformerId) {
        return transformerRepository.findById(transformerId).get().getPhysicalChemicalOilAnalysis();
    }

    public void createPhysicalChemicalOilAnalysis(PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis,
                                                  Long transformerId, String dateAnalysis) {
        System.out.println(dateAnalysis);
        physicalChemicalOilAnalysis.setDateAnalysis(DateUtil.stringToDate(dateAnalysis));
        physicalChemicalOilAnalysis.setTransformer(transformerRepository.getById(transformerId));
        physicalChemicalOilAnalysisRepository.save(physicalChemicalOilAnalysis);
    }

    public PhysicalChemicalOilAnalysis getPhysicalChemicalOilAnalysisById(Long id) {
        return physicalChemicalOilAnalysisRepository.getById(id);
    }

    public void updatePhysicalChemicalOilAnalysis(PhysicalChemicalOilAnalysis modifiedAnalysis) {
        PhysicalChemicalOilAnalysis oldAnalysis = physicalChemicalOilAnalysisRepository.getById(modifiedAnalysis.getId());
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
        try {
            physicalChemicalOilAnalysisRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {}
    }
}
