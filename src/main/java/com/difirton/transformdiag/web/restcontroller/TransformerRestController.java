package com.difirton.transformdiag.web.restcontroller;

import com.difirton.transformdiag.db.entity.Transformer;
import com.difirton.transformdiag.db.entity.TransformerCharacteristics;
import com.difirton.transformdiag.service.TransformerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "new/transformers",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TransformerRestController {
    private TransformerService transformerService;

    @Autowired
    public TransformerRestController(TransformerService transformerService) {
        this.transformerService = transformerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Transformer newTransformer (@Valid @RequestBody Transformer transformer) {
        return transformerService.saveTransformer(transformer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Transformer> findAllTransformer() {
        return transformerService.getAllTransformer();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Transformer findTransformerById(@PathVariable Long id) {
        return transformerService.getTransformerById(id);
    }
}
