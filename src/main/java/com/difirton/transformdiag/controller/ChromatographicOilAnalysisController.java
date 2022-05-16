package com.difirton.transformdiag.controller;

import com.difirton.transformdiag.entitys.ChromatographicOilAnalysis;
import com.difirton.transformdiag.repository.ChromatographicOilAnalysisRepository;
import com.difirton.transformdiag.repository.TransformerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/transformers/{transformerId}/chromatographic-oil-analyzes")
public class ChromatographicOilAnalysisController {
    @Autowired
    ChromatographicOilAnalysisRepository chromatographicOilAnalysisRepository;

    @Autowired
    private TransformerRepository transformerRepository;

    @GetMapping
    String findAllAnalyzes(Model model, @PathVariable("transformerId") Long transformerId) {
        model.addAttribute("analyzes", transformerRepository.findById(transformerId).get().getChromatographicOilAnalysis());
        model.addAttribute("transformerId", transformerId);
        return "transformers/chromatographicOilAnalyzes/show";
    }

    @GetMapping("/add")
    String addAnalysis(@ModelAttribute("analysis") ChromatographicOilAnalysis analysis, Model model, @PathVariable("transformerId") Long transformerId) {
        model.addAttribute("transformerId", transformerId);
        return "transformers/chromatographicOilAnalyzes/add";
    }

    @PostMapping
    String create(@Valid @ModelAttribute("analysis") ChromatographicOilAnalysis analysis, BindingResult bindingResult,
                  @PathVariable("transformerId") Long transformerId) {
        analysis.setTransformer(transformerRepository.getById(transformerId));
        if (bindingResult.hasErrors()) {
            return "/transformers/chromatographicOilAnalyzes/add";
        }
        chromatographicOilAnalysisRepository.save(analysis);
        return "redirect:/transformers/" + transformerId + "/chromatographic-oil-analyzes";
    }

    @GetMapping("/{idAnalysis}/edit")
    String edit(@PathVariable("idAnalysis") Long idAnalysis, Model model) {
        model.addAttribute("analysis", chromatographicOilAnalysisRepository.getById(idAnalysis));
        return "transformers/chromatographicOilAnalyzes/edit";
    }

    @PatchMapping
    String update(@Valid @ModelAttribute("analysis") ChromatographicOilAnalysis analysis, BindingResult bindingResult,
                  @PathVariable("transformerId") Long transformerId) {
        System.out.println(analysis.toString());
        if (bindingResult.hasErrors()) {
            return "transformers/edit";
        }
        analysis.setTransformer(transformerRepository.getById(transformerId));
        System.out.println(analysis.toString());
        chromatographicOilAnalysisRepository.save(analysis);
        return "redirect:/transformers/{transformerId}/chromatographic-oil-analyzes";
    }
}
