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

    public Transformer saveTransformer(Transformer transformer, TransformerCharacteristics characteristics) {
        characteristics.setTransformer(transformer);
        transformerCharacteristicsRepository.save(characteristics);
        return transformerRepository.save(transformer);
    }

    public Transformer getTransformerById(Long id) {
        return transformerRepository.findById(id).get();
    }

    public TransformerCharacteristics getTransformerCharacteristicsById(Long id) {
        return transformerCharacteristicsRepository.findByTransformerId(id);
    }

    public void deleteTransformerById(Long id) {
        transformerRepository.deleteById(id);
    }

    public Transformer saveTransformer(Transformer transformer) {
        System.out.println(transformer);
        System.out.println(transformer.getTransformerCharacteristics());
        transformerCharacteristicsRepository.save(transformer.getTransformerCharacteristics());
        return transformerRepository.save(transformer);
    }
}
