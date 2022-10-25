package com.difirton.transformer.db.repository;

import com.difirton.transformer.db.entity.TransformerCharacteristics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransformerCharacteristicsRepository extends JpaRepository<TransformerCharacteristics, Long> {
    TransformerCharacteristics findTransformerCharacteristicsByTransformerId(Long id);
}
