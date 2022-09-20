package com.difirton.transformdiag.db.repository;

import com.difirton.transformdiag.db.entity.ChromatographicOilAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChromatographicOilAnalysisRepository extends JpaRepository<ChromatographicOilAnalysis, Long> {
    List<ChromatographicOilAnalysis> findByTransformerId(Long id);
}
