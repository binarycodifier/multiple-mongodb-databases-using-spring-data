package org.binary.codifier.example;

import org.binary.codifier.example.model.Market;
import org.binary.codifier.example.model.Product;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractIntegrationTests {

    @Autowired
    MongoOperations marketTemplate;

    @Autowired
    MongoOperations productTemplate;

    Market fruitMarket;
    Product apple, orange, banana;

    @Before
    public void setUp() {
        deleteAllCollections();

        createMarket();
        createProducts();
    }

    private void deleteAllCollections() {
        marketTemplate.dropCollection(Market.class);
        productTemplate.dropCollection(Product.class);
    }

    private void createMarket() {
        fruitMarket = new Market();
        marketTemplate.save(fruitMarket);
    }

    private void createProducts() {
        apple = new Product("Apple");
        productTemplate.save(apple);

        orange = new Product("Orange");
        productTemplate.save(orange);

        banana = new Product("Banana");
        productTemplate.save(banana);
    }
}
