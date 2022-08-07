package com.difirton.transformdiag.web.restcontroller;

import com.difirton.transformdiag.db.entity.Transformer;
import com.difirton.transformdiag.service.TransformerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("new/transformers")
public class TransformerRestController {
    private TransformerService transformerService;

    @Autowired
    public TransformerRestController(TransformerService transformerService) {
        this.transformerService = transformerService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Transformer> findAllTransformer() {
        return transformerService.getAllTransformer();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Transformer findTransformerById(@PathVariable Long id) {
        return transformerService.getTransformerById(id);
    }
}
