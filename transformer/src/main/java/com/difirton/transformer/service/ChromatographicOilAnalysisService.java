package com.difirton.transformer.service;

import com.difirton.transformer.db.entity.ChromatographicOilAnalysis;
import com.difirton.transformer.db.repository.ChromatographicOilAnalysisRepository;
import com.difirton.transformer.db.repository.TransformerRepository;
import com.difirton.transformer.error.ChromatographicOilAnalysisNotFoundException;
import com.difirton.transformer.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChromatographicOilAnalysisService {
    private final ChromatographicOilAnalysisRepository chromatographicOilAnalysisRepository;
    private final TransformerRepository transformerRepository;
    private TransformerService transformerService;

    @Autowired
    public ChromatographicOilAnalysisService(ChromatographicOilAnalysisRepository chromatographicOilAnalysisRepository,
                                             TransformerRepository transformerRepository,
                                             TransformerService transformerService) {
        this.chromatographicOilAnalysisRepository = chromatographicOilAnalysisRepository;
        this.transformerRepository = transformerRepository;
    }
    
    public List<ChromatographicOilAnalysis> getAllChromatographicOilAnalysisByTransformerId(Long transformerId) {
        return chromatographicOilAnalysisRepository.findByTransformerId(transformerId);
    }

    @Transactional
    public void createChromatographicOilAnalysis(ChromatographicOilAnalysis chromatographicOilAnalysis,
                                                 Long transformerId, String dateAnalysis) {
        chromatographicOilAnalysis.setDateAnalysis(DateUtil.stringToDate(dateAnalysis));
        chromatographicOilAnalysis.setTransformer(transformerRepository.findById(transformerId).get());

        chromatographicOilAnalysisRepository.save(chromatographicOilAnalysis);
    }
    
    public ChromatographicOilAnalysis getChromatographicOilAnalysisById(Long id) {
        return chromatographicOilAnalysisRepository.findById(id)
                .orElseThrow(() -> new ChromatographicOilAnalysisNotFoundException(id));
    }

    public ChromatographicOilAnalysis updateChromatographicOilAnalysis(ChromatographicOilAnalysis modifiedAnalysis) {
        return chromatographicOilAnalysisRepository.findById(modifiedAnalysis.getId())
                .map(c -> {
                    c.setDateAnalysis(modifiedAnalysis.getDateAnalysis());
                    c.setHydrogenGasH2(modifiedAnalysis.getHydrogenGasH2());
                    c.setCarbonMonoxideCO(modifiedAnalysis.getCarbonMonoxideCO());
                    c.setCarbonDioxideCO2(modifiedAnalysis.getCarbonDioxideCO2());
                    c.setMethaneCH4(modifiedAnalysis.getMethaneCH4());
                    c.setEthaneC2H6(modifiedAnalysis.getEthaneC2H6());
                    c.setEthyleneC2H4(modifiedAnalysis.getEthyleneC2H4());
                    c.setAcetyleneC2H2(modifiedAnalysis.getAcetyleneC2H2());
                    c.setProtocolName(modifiedAnalysis.getProtocolName());
                    return chromatographicOilAnalysisRepository.save(c);
                })
                .orElseThrow(() -> new ChromatographicOilAnalysisNotFoundException(modifiedAnalysis.getId()));
    }

    public void deleteChromatographicOilAnalysisById(Long id) {
        chromatographicOilAnalysisRepository.deleteById(id);
    }
}
