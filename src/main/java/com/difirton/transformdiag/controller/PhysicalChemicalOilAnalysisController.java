package com.difirton.transformdiag.controller;

import com.difirton.transformdiag.entitys.PhysicalChemicalOilAnalysis;
import com.difirton.transformdiag.service.PhysicalChemicalOilAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/transformers/{transformerId}/physical-chemical-oil-analyzes")
public class PhysicalChemicalOilAnalysisController {
    PhysicalChemicalOilAnalysisService physicalChemicalOilAnalysisService;

    @Autowired
    public PhysicalChemicalOilAnalysisController(PhysicalChemicalOilAnalysisService physicalChemicalOilAnalysisService) {
        this.physicalChemicalOilAnalysisService = physicalChemicalOilAnalysisService;
    }

    @GetMapping
    String findAll(Model model, @PathVariable("transformerId") Long transformerId) {
        model.addAttribute("analyzes",physicalChemicalOilAnalysisService
                .getAllPhysicalChemicalOilAnalysisByTransformerId(transformerId));
        model.addAttribute("transformerId", transformerId);
        return "transformers/physicalChemicalOilAnalyzes/show";
    }

    @GetMapping("/add")
    String addAnalysis(@ModelAttribute("analysis") PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis, Model model,
                       @PathVariable("transformerId") Long transformerId) {
        model.addAttribute("transformerId", transformerId);
        return "transformers/physicalChemicalOilAnalyzes/add";
    }

    @PostMapping
    String create(@Valid @ModelAttribute("analysis") PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis,
                  BindingResult bindingResult,
                  @PathVariable("transformerId") Long transformerId) {
        physicalChemicalOilAnalysisService
                .setTransformerToPhysicalChemicalOilAnalysis(physicalChemicalOilAnalysis, transformerId);
        if (bindingResult.hasErrors()) {
            return "/transformers/physicalChemicalOilAnalyzes/add";
        }
        physicalChemicalOilAnalysisService.createPhysicalChemicalOilAnalysis(physicalChemicalOilAnalysis);
        return "redirect:/transformers/" + transformerId + "/physical-chemical-oil-analyzes";
    }

    @GetMapping("/{id}/edit")
    String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("analysis", physicalChemicalOilAnalysisService
                .getPhysicalChemicalOilAnalysisById(id));
        return "transformers/physicalChemicalOilAnalyzes/edit";
    }

    @PatchMapping("{id}")
    String update(@Valid @ModelAttribute("analysis") PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis,
                  BindingResult bindingResult,
                  @PathVariable("transformerId") Long transformerId, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "transformers/physicalChemicalOilAnalyzes/edit";
        }
        //analysis.setTransformer(transformerRepository.getById(transformerId));
        physicalChemicalOilAnalysisService.updatePhysicalChemicalOilAnalysis(physicalChemicalOilAnalysis);
        return "redirect:/transformers/" + transformerId + "/physical-chemical-oil-analyzes";
    }

    @DeleteMapping("{id}")
    String delete(@PathVariable("id") Long id, @PathVariable("transformerId") Long transformerId) {
        physicalChemicalOilAnalysisService.deletePhysicalChemicalOilAnalysisById(id);
        return "redirect:/transformers/" + transformerId + "/physical-chemical-oil-analyzes";
    }
}
