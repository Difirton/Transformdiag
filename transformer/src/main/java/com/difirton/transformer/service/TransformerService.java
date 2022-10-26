package com.difirton.transformer.service;

import com.difirton.transformer.db.entity.Transformer;
import com.difirton.transformer.db.entity.TransformerCharacteristics;
import com.difirton.transformer.db.repository.TransformerCharacteristicsRepository;
import com.difirton.transformer.db.repository.TransformerRepository;
import com.difirton.transformer.error.TransformerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Transformer saveTransformer(Transformer transformer, TransformerCharacteristics characteristics) {
        characteristics.setTransformer(transformer);
        transformerCharacteristicsRepository.save(characteristics);
        return transformerRepository.save(transformer);
    }

    public Transformer getTransformerById(Long id) {
        return transformerRepository.findById(id).orElseThrow(() -> new TransformerNotFoundException(id));
    }

    public Transformer updateTransformer(Long id, Transformer transformer) {
        return transformerRepository.findById(id)
                .map(t -> {
                    t.setKKS(transformer.getKKS());
                    t.setType(transformer.getType());
                    t.setFactoryNumber(transformer.getFactoryNumber());
                    return transformerRepository.save(t);
                })
                .orElseThrow(() -> new TransformerNotFoundException(id));
    }

    public TransformerCharacteristics getTransformerCharacteristicsById(Long id) {
        return transformerCharacteristicsRepository.findTransformerCharacteristicsByTransformerId(id);
    }

    public void deleteTransformerById(Long id) {
        transformerRepository.deleteById(id);
    }

    @Transactional
    public Transformer saveTransformer(Transformer transformer) {
        transformerCharacteristicsRepository.save(transformer.getTransformerCharacteristics());
        return transformerRepository.save(transformer);
    }
}
