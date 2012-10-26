package org.binary.codifier.example;

import com.mongodb.Mongo;
import org.binary.codifier.example.model.Currency;
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
    Mongo mongoInstance;

    @Autowired
    MongoOperations marketTemplate;

    @Autowired
    MongoOperations productTemplate;

    @Autowired
    MongoOperations currencyTemplate;

    Market fruitMarket;
    Product apple, orange, banana;
    Currency dollars, pounds, euros;

    @Before
    public void setUp() {
        deleteAllCollections();

        createCurrencies();
        createMarket();
        createProducts();
    }

    private void deleteAllCollections() {
        marketTemplate.dropCollection(Market.class);
        productTemplate.dropCollection(Product.class);
        currencyTemplate.dropCollection(Currency.class);
    }

    private void createCurrencies() {
        dollars = new Currency("$");
        currencyTemplate.save(dollars);

        pounds = new Currency("£");
        currencyTemplate.save(pounds);

        euros = new Currency("€");
        currencyTemplate.save(euros);
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
