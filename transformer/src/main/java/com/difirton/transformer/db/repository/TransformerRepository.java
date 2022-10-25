package com.difirton.transformer.db.repository;

import com.difirton.transformer.db.entity.Transformer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransformerRepository extends JpaRepository<Transformer, Long> {

}
