package com.difirton.transformdiag.db.repository;

import com.difirton.transformdiag.db.entity.ChromatographicOilAnalysis;
import com.difirton.transformdiag.db.entity.PhysicalChemicalOilAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChromatographicOilAnalysisRepository extends JpaRepository<ChromatographicOilAnalysis, Long> {
    List<ChromatographicOilAnalysis> findByTransformerId(Long id);
}
