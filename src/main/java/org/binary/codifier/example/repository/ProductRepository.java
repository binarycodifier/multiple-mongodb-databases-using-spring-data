package org.binary.codifier.example.repository;

import org.binary.codifier.example.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, ObjectId> {

    Product findByUniqueReference(String uniqueReference);
}
