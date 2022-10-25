package com.difirton.transformer.db.repository;

import com.difirton.transformer.db.entity.TransformerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransformerStatusRepository extends JpaRepository<TransformerStatus, Long> {
    Optional<TransformerStatus> findTransformerStatusByTransformerId(Long id);
}
