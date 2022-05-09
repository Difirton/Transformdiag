package com.difirton.transformdiag.controller;

import com.difirton.transformdiag.entitys.Transformer;
import com.difirton.transformdiag.repository.TransformerRepository;
import com.difirton.transformdiag.error.TransformerNotFoundException;
import com.difirton.transformdiag.error.TransformerUnSupportedFieldPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/transformers")
    public Transformer newTransformer(@RequestBody Transformer newTransformer) {
        return repository.save(newTransformer);
    }

    @GetMapping("/{id}")
    public Transformer findOne(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TransformerNotFoundException(id));
    }

    @PutMapping("/{id}")
    public Transformer saveOrUpdate(@RequestBody Transformer newTransformer, @PathVariable Long id) {
        return repository.findById(id)
                .map(x -> {
                    x.setKKS(newTransformer.getKKS());
                    x.setType(newTransformer.getType());
                    x.setFactoryNumber(newTransformer.getFactoryNumber());
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newTransformer.setId(id);
                    return repository.save(newTransformer);
                });
    }

    @PatchMapping("/{id}")
    public Transformer path(@RequestBody Map<String, String> update, @PathVariable Long id) {
        return repository.findById(id)
                .map(x -> {
                    String KKS = update.get("KKS");
                    if (!StringUtils.isEmpty(KKS)) {
                        x.setKKS(KKS);
                        return repository.save(x);
                    } else {
                        throw new TransformerUnSupportedFieldPatchException(update.keySet());
                    }
                }).orElseGet(() -> {
                    throw new TransformerNotFoundException(id);
                });
    }

    @DeleteMapping("/transformers/{id}")
    void deleteTransformer(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
