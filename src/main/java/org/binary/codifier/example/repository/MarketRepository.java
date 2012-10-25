package org.binary.codifier.example.repository;

import org.binary.codifier.example.model.Market;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketRepository extends MongoRepository<Market, ObjectId> {

}
