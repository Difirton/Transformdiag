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

    @GetMapping("/{id}")
    String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("transformer", repository.getById(id));
        return "transformers/show";
    }

    @GetMapping("/{id}/edit")
    String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("transformer", repository.getById(id));
        return "transformers/edit";
    }

    @PatchMapping("/{id}")
    String update(@Valid @ModelAttribute("transformer") Transformer transformer, BindingResult bindingResult,
                  @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "transformers/edit";
        }
        repository.save(transformer);
        return "redirect:/transformers/{id}";
    }

    @DeleteMapping("{id}")
    String deleteTransformer(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "redirect:/transformers";
    }
}
