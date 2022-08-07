package com.difirton.transformdiag.service;

import com.difirton.transformdiag.db.entity.Transformer;
import com.difirton.transformdiag.db.entity.TransformerCharacteristics;
import com.difirton.transformdiag.db.repository.TransformerCharacteristicsRepository;
import com.difirton.transformdiag.db.repository.TransformerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return transformerRepository.findById(id).get();
    }

    public TransformerCharacteristics getTransformerCharacteristicsById(Long id) {
        return transformerCharacteristicsRepository.findById(id).get();
    }

    public void deleteTransformerById(Long id) {
        transformerRepository.deleteById(id);
    }
}
