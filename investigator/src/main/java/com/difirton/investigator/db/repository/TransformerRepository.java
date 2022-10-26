package com.difirton.investigator.db.repository;

import com.difirton.investigator.db.entity.Transformer;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TransformerRepository extends MongoRepository<Transformer, String> {

}
