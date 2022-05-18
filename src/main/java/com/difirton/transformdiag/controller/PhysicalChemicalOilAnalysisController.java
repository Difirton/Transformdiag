package com.difirton.transformdiag.controller;

import com.difirton.transformdiag.entitys.PhysicalChemicalOilAnalysis;
import com.difirton.transformdiag.repository.PhysicalChemicalOilAnalysisRepository;
import com.difirton.transformdiag.repository.TransformerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/transformers/{transformerId}/physical-chemical-oil-analyzes")
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
        model.addAttribute("analyzes",transformerRepository.getById(transformerId).getPhysicalChemicalOilAnalysis());
        model.addAttribute("transformerId", transformerId);
        return "transformers/physicalChemicalOilAnalyzes/show";
    }

    @GetMapping("/add")
    String addAnalysis(@ModelAttribute("analysis") PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis, Model model, @PathVariable("transformerId") Long transformerId) {
        model.addAttribute("transformerId", transformerId);
        return "transformers/physicalChemicalOilAnalyzes/add";
    }

    @PostMapping
    String create(@Valid @ModelAttribute("analysis") PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis, BindingResult bindingResult,
                  @PathVariable("transformerId") Long transformerId) {
        physicalChemicalOilAnalysis.setTransformer(transformerRepository.getById(transformerId));
        if (bindingResult.hasErrors()) {
            return "/transformers/physicalChemicalOilAnalyzes/add";
        }
        physicalChemicalOilAnalysisRepository.save(physicalChemicalOilAnalysis);
        return "redirect:/transformers/" + transformerId + "/physical-chemical-oil-analyzes";
    }

    @GetMapping("/{id}/edit")
    String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("analysis", physicalChemicalOilAnalysisRepository.getById(id));
        return "transformers/physicalChemicalOilAnalyzes/edit";
    }

    @PatchMapping("{id}")
    String update(@Valid @ModelAttribute("analysis") PhysicalChemicalOilAnalysis analysis, BindingResult bindingResult,
                  @PathVariable("transformerId") Long transformerId, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "transformers/physicalChemicalOilAnalysis/edit";
        }
        analysis.setTransformer(transformerRepository.getById(transformerId));
        physicalChemicalOilAnalysisRepository.save(analysis);
        return "redirect:/transformers/" + transformerId + "/physical-chemical-oil-analyzes";
    }

    @DeleteMapping("{id}")
    String delete(@PathVariable("id") Long id, @PathVariable("transformerId") Long transformerId) {
        physicalChemicalOilAnalysisRepository.deleteById(id);
        return "redirect:/transformers/" + transformerId + "/physical-chemical-oil-analyzes";
    }

}
