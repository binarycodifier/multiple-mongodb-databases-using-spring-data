package org.binary.codifier.example.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product {
    @Id
    private ObjectId id;
    private String uniqueReference;

    public Product(String uniqueReference) {
        setUniqueReference(uniqueReference);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUniqueReference() {
        return uniqueReference;
    }

    public void setUniqueReference(String uniqueReference) {
        this.uniqueReference = uniqueReference;
    }
}
