package com.difirton.transformdiag.controller;

import com.difirton.transformdiag.entitys.Transformer;
import com.difirton.transformdiag.entitys.TransformerCharacteristics;
import com.difirton.transformdiag.repository.TransformerCharacteristicsRepository;
import com.difirton.transformdiag.repository.TransformerRepository;
import com.difirton.transformdiag.service.TransformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/transformers")
public class TransformerController {
    private TransformerService transformerService;

    @Autowired
    public TransformerController(TransformerService transformerService) {
        this.transformerService = transformerService;
    }

    @GetMapping
    String findAll(Model model) {
        model.addAttribute("transformers", transformerService.getAllTransformer());
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
        model.addAttribute("characteristics", transformerRepository.getById(transformerId)
                                                                               .getTransformerCharacteristics());
        return "transformers/edit";
    }

    @PatchMapping("/{id}")
    String update(@Valid @ModelAttribute("transformer") Transformer transformer, BindingResult bindingResultTransform,
                  @Valid @ModelAttribute("characteristics") TransformerCharacteristics characteristics,
                  BindingResult bindingResultCharacteristics, @PathVariable Long id) {
        if (bindingResultTransform.hasErrors() || (bindingResultCharacteristics.hasErrors())) {
            return "transformers/add";
        }
        transformerRepository.save(transformer);
        characteristics.setTransformer(transformer);
        transformerCharacteristicsRepository.save(characteristics);
        return "redirect:/transformers";
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    String deleteTransformer(@PathVariable("id") Long transformerId) {
        try {
            transformerRepository.deleteById(transformerId);
        } catch (EmptyResultDataAccessException e) {}
        return "redirect:/transformers";
    }
}
