package com.difirton.transformdiag.controller;

import com.difirton.transformdiag.entitys.Transformer;
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
    @Autowired
    private TransformerRepository repository;

    @GetMapping
    String findAll(Model model) {
        model.addAttribute("transformers", repository.findAll());
        return "transformers/transformers";
    }

    @GetMapping("/add")
    String add(@ModelAttribute("transformer") Transformer transformer) {
        return "transformers/add";
    }

    @PostMapping()
    String create(@Valid @ModelAttribute("transformer") Transformer transformer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "transformers/add";
        }
        repository.save(transformer);
        return "redirect:/transformers";
    }

    @GetMapping("/{transformerId}")
    String show(@PathVariable("transformerId") Long transformerId, Model model) {
        model.addAttribute("transformer", repository.getById(transformerId));
        return "transformers/show";
    }

    @GetMapping("/{transformerId}/edit")
    String edit(@PathVariable("transformerId") Long transformerId, Model model) {
        model.addAttribute("transformer", repository.getById(transformerId));
        return "transformers/edit";
    }

    @PatchMapping("/{Id}")
    String update(@Valid @ModelAttribute("transformer") Transformer transformer, BindingResult bindingResult,
                  @PathVariable Long Id) {
        if (bindingResult.hasErrors()) {
            return "transformers/edit";
        }
        repository.save(transformer);
        return "redirect:/transformers/{Id}";
    }

    @DeleteMapping("{Id}")
    String deleteTransformer(@PathVariable("Id") Long transformerId) {
        repository.deleteById(transformerId);
        return "redirect:/transformers";
    }
}
