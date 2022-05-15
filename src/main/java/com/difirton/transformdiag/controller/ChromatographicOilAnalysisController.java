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
@RequestMapping("/transformers/{id}/chromatographic-oil-analyzes")
public class ChromatographicOilAnalysisController {
    @Autowired
    ChromatographicOilAnalysisRepository chromatographicOilAnalysisRepository;

    @Autowired
    private TransformerRepository transformerRepository;

    @GetMapping
    String findAllAnalyzes(Model model, @PathVariable("id") Long id) {
        model.addAttribute("analyzes", transformerRepository.findById(id).get().getChromatographicOilAnalysis());
        model.addAttribute("transformerId", id);
        return "/transformers/chromatographicOilAnalyzes/show";
    }

    @GetMapping("/add")
    String addAnalysis(@ModelAttribute("analysis") ChromatographicOilAnalysis analysis, Model model, @PathVariable("id") Long id) {
        model.addAttribute("transformerId", id);
        return "/transformers/chromatographicOilAnalyzes/add";
    }

    @PostMapping
    String create(@Valid @ModelAttribute("analysis") ChromatographicOilAnalysis analysis, BindingResult bindingResult, @PathVariable("id") Long id) {
        System.out.println(analysis.toString());
        analysis.setTransformer(transformerRepository.getById(id));
        if (bindingResult.hasErrors()) {
            return "/transformers/chromatographicOilAnalyzes/add";
        }
        chromatographicOilAnalysisRepository.save(analysis);
        return "redirect:/transformers/" + id + "/chromatographic-oil-analyzes";
    }
}
