package com.difirton.investigator.db.repository;

import com.difirton.investigator.db.entity.TransformerStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TransformerStatusRepository extends MongoRepository<TransformerStatus, String> {

}
