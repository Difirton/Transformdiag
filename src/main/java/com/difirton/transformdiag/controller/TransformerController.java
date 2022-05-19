package com.difirton.transformdiag.controller;

import com.difirton.transformdiag.entitys.Transformer;
import com.difirton.transformdiag.entitys.TransformerCharacteristics;
import com.difirton.transformdiag.repository.TransformerCharacteristicsRepository;
import com.difirton.transformdiag.repository.TransformerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/transformers")
public class TransformerController {
    private TransformerRepository transformerRepository;
    private TransformerCharacteristicsRepository transformerCharacteristicsRepository;

    @Autowired
    public TransformerController(TransformerRepository transformerRepository, TransformerCharacteristicsRepository transformerCharacteristicsRepository) {
        this.transformerRepository = transformerRepository;
        this.transformerCharacteristicsRepository = transformerCharacteristicsRepository;
    }

    @GetMapping
    String findAll(Model model) {
        model.addAttribute("transformers", transformerRepository.findAll());
        return "transformers/transformers";
    }

    @GetMapping("/add")
    String add(@ModelAttribute("transformer") Transformer transformer,
               @ModelAttribute("characteristics") TransformerCharacteristics characteristics) {
        return "transformers/add";
    }

    @PostMapping
    String create(@Valid @ModelAttribute("transformer") Transformer transformer, BindingResult bindingResultTransform,
                  @Valid @ModelAttribute("characteristics") TransformerCharacteristics characteristics,
                  BindingResult bindingResultCharacteristics) {
        if (bindingResultTransform.hasErrors() || (bindingResultCharacteristics.hasErrors())) {
            return "transformers/add";
        }
        transformerRepository.save(transformer);
        characteristics.setTransformer(transformer);
        transformerCharacteristicsRepository.save(characteristics);
        return "redirect:/transformers";
    }

    @GetMapping("/{transformerId}")
    String show(@PathVariable("transformerId") Long transformerId, Model model) {
        model.addAttribute("transformer", transformerRepository.getById(transformerId));
        model.addAttribute("transformerCharacteristics",
                transformerRepository.getById(transformerId).getTransformerCharacteristics());
        return "transformers/show";
    }

    @GetMapping("/{transformerId}/edit")
    String edit(@PathVariable("transformerId") Long transformerId, Model model) {
        model.addAttribute("transformer", transformerRepository.getById(transformerId));
        return "transformers/edit";
    }

    @PatchMapping("/{id}")
    String update(@Valid @ModelAttribute("transformer") Transformer transformer, BindingResult bindingResult,
                  @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "transformers/edit";
        }
        transformerRepository.save(transformer);
        return "redirect:/transformers/{id}";
    }

    @DeleteMapping("{id}")
    String deleteTransformer(@PathVariable("id") Long transformerId) {
        transformerRepository.deleteById(transformerId);
        return "redirect:/transformers";
    }
}
