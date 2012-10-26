package org.binary.codifier.example.repository;

import org.binary.codifier.example.model.Currency;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends MongoRepository<Currency, ObjectId> {

    Currency findBySymbol(String symbol);
}
