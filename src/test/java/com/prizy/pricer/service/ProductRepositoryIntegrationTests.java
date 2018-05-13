package com.prizy.pricer.service;

import com.prizy.pricer.ApplicationEntry;
import com.prizy.pricer.domain.Product;
import com.prizy.pricer.service.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationEntry.class)
public class ProductRepositoryIntegrationTests {

	@Autowired
	
    ProductRepository repository;

    @Test
    public void getProductById(){
        Product product = this.repository.findOne(1l);
        assertThat(product.getProductId(), is(equalTo(1l)));
        assertThat(product.getBarcode(), is(equalTo("B12A33")));
    }

    @Test
    public void saveProduct(){
        Product product = new Product();
        product.setBarcode("BXXXLL2222L");
        product.setName("Demo Product");

        Product savedProduct = repository.save(product);

        assertThat(savedProduct.getProductId(), notNullValue());
        assertThat(savedProduct.getBarcode(), is(equalTo(product.getBarcode())));
        assertThat(savedProduct.getName(), is(equalTo(product.getName())));

    }
}
