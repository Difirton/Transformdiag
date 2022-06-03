package com.difirton.transformdiag.service;

import com.difirton.transformdiag.entitys.Transformer;
import com.difirton.transformdiag.entitys.TransformerCharacteristics;
import com.difirton.transformdiag.repository.TransformerCharacteristicsRepository;
import com.difirton.transformdiag.repository.TransformerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransformerService {
    private TransformerRepository transformerRepository;
    private TransformerCharacteristicsRepository transformerCharacteristicsRepository;

    @Autowired
    public TransformerService(TransformerRepository transformerRepository,
                              TransformerCharacteristicsRepository transformerCharacteristicsRepository) {
        this.transformerRepository = transformerRepository;
        this.transformerCharacteristicsRepository = transformerCharacteristicsRepository;
    }

    public List<Transformer> getAllTransformer() {
        return transformerRepository.findAll();
    }

    public void saveTransformer(Transformer transformer, TransformerCharacteristics characteristics) {
        transformerRepository.save(transformer);
        characteristics.setTransformer(transformer);
        transformerCharacteristicsRepository.save(characteristics);
    }

    public Transformer getTransformerById(Long id) {
        return transformerRepository.getById(id);
    }

    public TransformerCharacteristics getTransformerCharacteristicsById(Long id) {
        return transformerCharacteristicsRepository.getById(id);
    }

    public void deleteTransformerById(Long id) {
        try {
            transformerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {}
    }

}
