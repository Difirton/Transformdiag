package com.difirton.transformer.db.repository;

import com.difirton.transformer.db.entity.ChromatographicOilAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChromatographicOilAnalysisRepository extends JpaRepository<ChromatographicOilAnalysis, Long> {
    List<ChromatographicOilAnalysis> findByTransformerId(Long id);
}
