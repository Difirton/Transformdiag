package com.difirton.transformdiag.controller;

import com.difirton.transformdiag.entitys.Transformer;
import com.difirton.transformdiag.repository.TransformerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/transformers")
public class TransformerController {
    @Autowired
    private TransformerRepository repository;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("transformers", repository.findAll());
        return "transformers";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("transformer") Transformer newTransformer) {
        return "add";
    }

    @PostMapping()
    public String create(@ModelAttribute("transformer") Transformer newTransformer) {
        repository.save(newTransformer);
        return "redirect:/transformers";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("transformer", repository.getById(id));
        return "show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("transformer", repository.getById(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    String update(@ModelAttribute("transformer") Transformer updateTransformer, @PathVariable Long id) {
        repository.save(updateTransformer);
        return "redirect:/transformers/{id}";
    }

    @DeleteMapping("{id}")
    String deleteTransformer(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "redirect:/transformers";
    }
}
