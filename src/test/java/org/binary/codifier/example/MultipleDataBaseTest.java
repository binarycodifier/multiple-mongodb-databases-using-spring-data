package org.binary.codifier.example;

import org.binary.codifier.example.model.Market;
import org.binary.codifier.example.model.Product;
import org.binary.codifier.example.repository.MarketRepository;
import org.binary.codifier.example.repository.ProductRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration("classpath:/application-context.xml")
public class MultipleDataBaseTest extends AbstractIntegrationTests {

    @Autowired
    MarketRepository marketRepository;

    @Autowired
    ProductRepository productRepository;

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
    public void shouldAddProductsToExistingMarket() {
        fruitMarket.addProduct(apple);
        fruitMarket.addProduct(orange);
        fruitMarket.addProduct(banana);
        marketRepository.save(fruitMarket);

        Market savedMarket = marketRepository.findOne(fruitMarket.getId());
        assertThat(savedMarket.getId(), is(fruitMarket.getId()));
        assertThat(savedMarket.getProducts().size(), is(3));
    }

    @Test
    public void shouldCreateANewMarketWithNewProducts() {
        Product vanillaIceCream = new Product("Vanilla Ice Cream");
        productRepository.save(vanillaIceCream);

        Product persistedVanillaIceCream = productRepository.findByUniqueReference(vanillaIceCream.getUniqueReference());
        assertThat(persistedVanillaIceCream.getId(), is(vanillaIceCream.getId()));

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
    }
}
