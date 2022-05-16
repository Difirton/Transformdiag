package com.difirton.transformdiag.service;

import com.difirton.transformdiag.entitys.ChromatographicOilAnalysis;
import com.difirton.transformdiag.repository.ChromatographicOilAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ChromatographicOilAnalysisService {
    ChromatographicOilAnalysisRepository chromatographicOilAnalysisRepository;

    @Autowired
    public ChromatographicOilAnalysisService(ChromatographicOilAnalysisRepository chromatographicOilAnalysisRepository) {
        this.chromatographicOilAnalysisRepository = chromatographicOilAnalysisRepository;
    }

    public ChromatographicOilAnalysis getChromatographicOilAnalysis(Long id) {
        System.out.println(chromatographicOilAnalysisRepository.getById(id));
        return chromatographicOilAnalysisRepository.getById(id);
    }
}
