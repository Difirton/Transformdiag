package com.difirton.transformdiag.controller;

import com.difirton.transformdiag.repository.PhysicalChemicalOilAnalysisRepository;
import com.difirton.transformdiag.repository.TransformerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transformers/{transformerId}/physical-chemical-oil-analysis")
public class PhysicalChemicalOilAnalysisController {
    PhysicalChemicalOilAnalysisRepository physicalChemicalOilAnalysisRepository;
    TransformerRepository transformerRepository;

    @Autowired
    public PhysicalChemicalOilAnalysisController(PhysicalChemicalOilAnalysisRepository physicalChemicalOilAnalysisRepository, TransformerRepository transformerRepository) {
        this.physicalChemicalOilAnalysisRepository = physicalChemicalOilAnalysisRepository;
        this.transformerRepository = transformerRepository;
    }

    @GetMapping
    String findAll(Model model, @PathVariable("transformerId") Long transformerId) {
        model.addAttribute(transformerRepository.getById(transformerId).getPhysicalChemicalAnalysisOils());
        model.addAttribute("transformerId", transformerId);
        return "transformers/physicalChemicalOilAnalysis/show";
    }
}
