package com.difirton.transformdiag.db.repository;

import com.difirton.transformdiag.db.entity.PhysicalChemicalOilAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhysicalChemicalOilAnalysisRepository extends JpaRepository<PhysicalChemicalOilAnalysis, Long> {
    List<PhysicalChemicalOilAnalysis> findByTransformerId(Long id);
}
