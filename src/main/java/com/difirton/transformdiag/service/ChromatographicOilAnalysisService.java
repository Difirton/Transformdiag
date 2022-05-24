package com.difirton.transformdiag.service;

import com.difirton.transformdiag.entitys.ChromatographicOilAnalysis;
import com.difirton.transformdiag.repository.ChromatographicOilAnalysisRepository;
import com.difirton.transformdiag.repository.TransformerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChromatographicOilAnalysisService {
    private ChromatographicOilAnalysisRepository chromatographicOilAnalysisRepository;
    private TransformerRepository transformerRepository;

    @Autowired
    public ChromatographicOilAnalysisService(ChromatographicOilAnalysisRepository chromatographicOilAnalysisRepository,
                                             TransformerRepository transformerRepository) {
        this.chromatographicOilAnalysisRepository = chromatographicOilAnalysisRepository;
        this.transformerRepository = transformerRepository;
    }
    
    public List<ChromatographicOilAnalysis> getAllChromatographicOilAnalysisByTransformerId(Long transformerId) {
        return transformerRepository.findById(transformerId).get().getChromatographicOilAnalysis();
    }
    
    public void setTransformerToChromatographicOilAnalysis(ChromatographicOilAnalysis chromatographicOilAnalysis, 
                                                           Long transformerId) {
        chromatographicOilAnalysis.setTransformer(transformerRepository.getById(transformerId));
    }
    
    public void createChromatographicOilAnalysis(ChromatographicOilAnalysis chromatographicOilAnalysis) {
        chromatographicOilAnalysisRepository.save(chromatographicOilAnalysis);
    }
    
    public ChromatographicOilAnalysis getChromatographicOilAnalysisById(Long id) {
        return chromatographicOilAnalysisRepository.getById(id);
    }
    
    public void updateChromatographicOilAnalysis(ChromatographicOilAnalysis modifiedAnalysis) {
        ChromatographicOilAnalysis oldAnalysis = chromatographicOilAnalysisRepository.getById(modifiedAnalysis.getId());
        if (modifiedAnalysis.getDateAnalysis() != null ||
                !modifiedAnalysis.getDateAnalysis().equals(oldAnalysis.getDateAnalysis())) {
            oldAnalysis.setDateAnalysis(modifiedAnalysis.getDateAnalysis());
        }
        if (modifiedAnalysis.getHydrogenGasH2() != null ||
                !modifiedAnalysis.getHydrogenGasH2().equals(oldAnalysis.getHydrogenGasH2())) {
            oldAnalysis.setHydrogenGasH2(modifiedAnalysis.getHydrogenGasH2());
        }
        if (modifiedAnalysis.getCarbonMonoxideCO() != null ||
                !modifiedAnalysis.getCarbonMonoxideCO().equals(oldAnalysis.getCarbonMonoxideCO())) {
            oldAnalysis.setCarbonMonoxideCO(modifiedAnalysis.getCarbonMonoxideCO());
        }
        if (modifiedAnalysis.getCarbonDioxideCO2() != null ||
                !modifiedAnalysis.getCarbonDioxideCO2().equals(oldAnalysis.getCarbonDioxideCO2())) {
            oldAnalysis.setCarbonDioxideCO2(modifiedAnalysis.getCarbonDioxideCO2());
        }
        if (modifiedAnalysis.getMethaneCH4() != null ||
                !modifiedAnalysis.getMethaneCH4().equals(oldAnalysis.getMethaneCH4())) {
            oldAnalysis.setMethaneCH4(modifiedAnalysis.getMethaneCH4());
        }
        if (modifiedAnalysis.getEthaneC2H6() != null ||
                !modifiedAnalysis.getEthaneC2H6().equals(oldAnalysis.getEthaneC2H6())) {
            oldAnalysis.setEthaneC2H6(modifiedAnalysis.getEthaneC2H6());
        }
        if (modifiedAnalysis.getEthyleneC2H4() != null ||
                !modifiedAnalysis.getEthyleneC2H4().equals(oldAnalysis.getEthyleneC2H4())) {
            oldAnalysis.setEthyleneC2H4(modifiedAnalysis.getEthyleneC2H4());
        }
        if (modifiedAnalysis.getAcetyleneC2H2() != null ||
                !modifiedAnalysis.getAcetyleneC2H2().equals(oldAnalysis.getAcetyleneC2H2())) {
            oldAnalysis.setAcetyleneC2H2(modifiedAnalysis.getAcetyleneC2H2());
        }
        if (modifiedAnalysis.getProtocolName() != null ||
                !modifiedAnalysis.getProtocolName().equals(oldAnalysis.getProtocolName())) {
            oldAnalysis.setProtocolName(modifiedAnalysis.getProtocolName());
        }
        chromatographicOilAnalysisRepository.save(oldAnalysis);
    }

    public void deleteChromatographicOilAnalysisById(Long id) {
        try {
            chromatographicOilAnalysisRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {}
    }

}
