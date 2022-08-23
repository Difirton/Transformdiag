package com.difirton.transformdiag.web.controller;

import com.difirton.transformdiag.db.entity.ChromatographicOilAnalysis;
import com.difirton.transformdiag.service.ChromatographicOilAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/v1/transformers/{transformerId}/chromatographic-oil-analyzes")
public class ChromatographicOilAnalysisController {
    private ChromatographicOilAnalysisService chromatographicOilAnalysisService;
    private String date;

    @Autowired
    public ChromatographicOilAnalysisController(ChromatographicOilAnalysisService chromatographicOilAnalysisService) {
        this.chromatographicOilAnalysisService = chromatographicOilAnalysisService;
    }

    @GetMapping
    String findAllAnalyzes(Model model, @PathVariable("transformerId") Long transformerId) {
        model.addAttribute("analyzes",
                chromatographicOilAnalysisService.getAllChromatographicOilAnalysisByTransformerId(transformerId));
        return "transformers/chromatographicOilAnalyzes/show";
    }

    @GetMapping("/add")
    String addAnalysis(@ModelAttribute("analysis") ChromatographicOilAnalysis analysis,
                       @ModelAttribute("date") String date, Model model,
                       @PathVariable("transformerId") Long transformerId) {
        model.addAttribute("date", date);
        model.addAttribute("transformerId", transformerId);
        return "transformers/chromatographicOilAnalyzes/add";
    }

    @PostMapping
    String create(@Valid @ModelAttribute("analysis") ChromatographicOilAnalysis chromatographicOilAnalysis,
                  BindingResult bindingResult,
                  @ModelAttribute("date") String date,
                  @PathVariable("transformerId") Long transformerId) {
        if (bindingResult.hasErrors()) {
            return "/transformers/chromatographicOilAnalyzes/add";
        }
        chromatographicOilAnalysisService.createChromatographicOilAnalysis(chromatographicOilAnalysis,
                transformerId, date);
        return "redirect:/transformers/" + transformerId + "/chromatographic-oil-analyzes";
    }

    @GetMapping("/{id}/edit")
    String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("analysis", chromatographicOilAnalysisService
                .getChromatographicOilAnalysisById(id));
        return "transformers/chromatographicOilAnalyzes/edit";
    }

    @PatchMapping("{id}")
    String update(@Valid @ModelAttribute("analysis") ChromatographicOilAnalysis chromatographicOilAnalysis,
                  BindingResult bindingResult, @PathVariable("transformerId") Long transformerId,
                  @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "transformers/chromatographicOilAnalyzes/edit";
        }
        chromatographicOilAnalysisService.updateChromatographicOilAnalysis(chromatographicOilAnalysis);
        return "redirect:/transformers/" + transformerId + "/chromatographic-oil-analyzes";
    }

    @DeleteMapping("{id}")
    String delete(@PathVariable("id") Long id, @PathVariable("transformerId") Long transformerId) {
        chromatographicOilAnalysisService.deleteChromatographicOilAnalysisById(id);
        return "redirect:/transformers/" + transformerId + "/chromatographic-oil-analyzes";
    }
}
