package com.difirton.transformdiag.db.repository;

import com.difirton.transformdiag.db.entity.PhysicalChemicalOilAnalysis;
import com.difirton.transformdiag.db.entity.TransformerCharacteristics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransformerCharacteristicsRepository extends JpaRepository<TransformerCharacteristics, Long> {
    TransformerCharacteristics findByTransformerId(Long id);
}
