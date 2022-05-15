package com.difirton.transformdiag.repository;

import com.difirton.transformdiag.entitys.ChromatographicOilAnalysis;
import com.difirton.transformdiag.entitys.Transformer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransformerRepository extends JpaRepository<Transformer, Long> {
}
