package org.binary.codifier.example;

import com.mongodb.*;
import org.binary.codifier.example.model.Currency;
import org.binary.codifier.example.model.Market;
import org.binary.codifier.example.model.Product;
import org.binary.codifier.example.repository.CurrencyRepository;
import org.binary.codifier.example.repository.MarketRepository;
import org.binary.codifier.example.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.net.UnknownHostException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@ContextConfiguration("classpath:/application-context.xml")
public class MultipleDataBaseTest extends AbstractIntegrationTests {

    @Autowired
    MarketRepository marketRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Test
    public void shouldHaveFruitMarketInOwnDataStore() {
        Market result = marketRepository.findOne(fruitMarket.getId());
        assertThat(result.getId(), is(fruitMarket.getId()));
    }

    @Test
    public void shouldHaveProductsInOwnDataStore() {
        Product appleProduct = productRepository.findByUniqueReference(apple.getUniqueReference());
        assertThat(appleProduct.getId(), is(apple.getId()));

        Product orangeProduct = productRepository.findByUniqueReference(orange.getUniqueReference());
        assertThat(orangeProduct.getId(), is(orange.getId()));

        Product bananaProduct = productRepository.findByUniqueReference(banana.getUniqueReference());
        assertThat(bananaProduct.getId(), is(banana.getId()));
    }

    @Test
    public void shouldHaveCurrenciesInOwnDataStore() {
        Currency dollarCurrency = currencyRepository.findBySymbol(dollars.getSymbol());
        assertThat(dollarCurrency.getId(), is(dollars.getId()));

        Currency poundsCurrency = currencyRepository.findBySymbol(pounds.getSymbol());
        assertThat(poundsCurrency.getId(), is(pounds.getId()));

        Currency euroCurrency = currencyRepository.findBySymbol(euros.getSymbol());
        assertThat(euroCurrency.getId(), is(euros.getId()));
    }

    @Test
    public void shouldAddCurrencyToProduct() {
        apple.setCurrency(dollars);
        productRepository.save(apple);

        Product savedApple = productRepository.findByUniqueReference(apple.getUniqueReference());
        assertThat(savedApple.getId(), is(apple.getId()));
        assertThat(savedApple.getCurrency(), is(notNullValue()));
    }

    @Test
    public void shouldAddProductsToExistingMarket() {
        fruitMarket.addProduct(apple);
        fruitMarket.addProduct(orange);
        fruitMarket.addProduct(banana);
        marketRepository.save(fruitMarket);

        Market savedMarket = marketRepository.findOne(fruitMarket.getId());
        assertThat(savedMarket.getId(), is(fruitMarket.getId()));
        assertThat(savedMarket.getProducts().size(), is(3));
        assertThat(savedMarket.getProducts().get(0), is(notNullValue()));
    }

    @Test
    public void shouldCreateANewMarketWithNewProducts() {
        Product vanillaIceCream = new Product("Vanilla Ice Cream");
        productRepository.save(vanillaIceCream);

        Product savedVanillaIceCream = productRepository.findByUniqueReference(vanillaIceCream.getUniqueReference());
        assertThat(savedVanillaIceCream.getId(), is(vanillaIceCream.getId()));

        Product chocolateIceCream = new Product("Chocolate Ice Cream");
        productRepository.save(chocolateIceCream);

        Product savedChocolateIceCream = productRepository.findByUniqueReference(chocolateIceCream.getUniqueReference());
        assertThat(savedChocolateIceCream.getId(), is(chocolateIceCream.getId()));

        Market iceCreamMarket = new Market();
        iceCreamMarket.addProduct(vanillaIceCream);
        iceCreamMarket.addProduct(chocolateIceCream);
        marketRepository.save(iceCreamMarket);

        Market savedMarket = marketRepository.findOne(iceCreamMarket.getId());
        assertThat(savedMarket.getId(), is(iceCreamMarket.getId()));
        assertThat(savedMarket.getProducts().size(), is(2));
        assertThat(savedMarket.getProducts().get(0), is(notNullValue()));
    }

    @Test
    public void shouldRetrieveSingleDbRefAssociationUsingSpringDataWithMongoDataStructures() {
        orange.setCurrency(pounds);
        productRepository.save(orange);

        DBCollection productCollection = productTemplate.getCollection("product");
        assertThat(productCollection, is(notNullValue()));

        DBObject orangeProductDataStructure = productCollection.findOne(new BasicDBObject("uniqueReference", orange.getUniqueReference()));

        Object associatedCurrencyDataStructure = orangeProductDataStructure.get("currency");
        assertThat(associatedCurrencyDataStructure, is(notNullValue()));
        assertTrue(associatedCurrencyDataStructure instanceof DBRef);

        DBRef associatedCurrencyDataStructureAsDBRef = (DBRef)associatedCurrencyDataStructure;
        assertThat(associatedCurrencyDataStructureAsDBRef.getRef(), is(notNullValue()));
        assertThat(associatedCurrencyDataStructureAsDBRef.getId(), is(notNullValue()));
        assertThat(associatedCurrencyDataStructureAsDBRef.getDB(), is(notNullValue()));

        assertThat(associatedCurrencyDataStructureAsDBRef.getDB().getName(), is("currency"));
    }

    @Test
    public void shouldSaveAndRetrieveSingleDbRefAssociationUsingMongoDBDriverWithMongoDataStructures() throws Exception {
        //save a new yen currency
        DB currencyDatabase = mongoInstance.getDB("currency");
        assertThat(currencyDatabase, is(notNullValue()));

        DBCollection currencyCollection = currencyDatabase.getCollection("currency");

        BasicDBObject yenCurrency = new BasicDBObject();
        ObjectId currencyId = new ObjectId();
        yenCurrency.put("_id", currencyId);
        yenCurrency.put("symbol", "¥");
        currencyCollection.insert(yenCurrency);

        BasicDBObject savedYenCurrency = (BasicDBObject) currencyCollection.findOne(new BasicDBObject("symbol", yenCurrency.get("symbol")));
        assertThat(savedYenCurrency, is(notNullValue()));
        assertThat(savedYenCurrency.get("symbol"), is(yenCurrency.get("symbol")));

        //save a new pear product with new yen currency
        DB productDatabase = mongoInstance.getDB("product");
        assertThat(productDatabase, is(notNullValue()));

        DBCollection productCollection = productDatabase.getCollection("product");

        BasicDBObject pearProduct = new BasicDBObject();
        ObjectId productId = new ObjectId();
        pearProduct.put("_id", productId);
        pearProduct.put("uniqueReference", "Pear");
        pearProduct.put("currency", new DBRef(currencyDatabase, currencyCollection.getName(), currencyId));
        productCollection.insert(pearProduct);

        //check the DbRef before persisting to the data store
        assertDbRefAssociation(pearProduct);

        BasicDBObject savedPearProduct = (BasicDBObject) productCollection.findOne(new BasicDBObject("uniqueReference", pearProduct.get("uniqueReference")));
        assertThat(savedPearProduct, is(notNullValue()));
        assertThat(savedPearProduct.get("uniqueReference"), is(pearProduct.get("uniqueReference")));

        //check the DbRef association
        assertDbRefAssociation(savedPearProduct);
    }

    private void assertDbRefAssociation(BasicDBObject pearProduct) {
        Object pearProductCurrency = pearProduct.get("currency");
        assertThat(pearProductCurrency, is(notNullValue()));
        assertTrue(pearProductCurrency instanceof DBRef);

        DBRef pearProductCurrencyAsDBRef = (DBRef)pearProductCurrency;
        assertThat(pearProductCurrencyAsDBRef.getRef(), is(notNullValue()));
        assertThat(pearProductCurrencyAsDBRef.getId(), is(notNullValue()));
        assertThat(pearProductCurrencyAsDBRef.getDB(), is(notNullValue()));

        assertThat(pearProductCurrencyAsDBRef.getDB().getName(), is("currency"));
    }
}
