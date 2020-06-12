package com.example.productservice.repository;

import com.example.productservice.IntegrationBaseTest;
import com.example.productservice.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductRepositoryTest extends IntegrationBaseTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void given_existing_product_find_by_name() {
        String name = "product1";
        String description = "description product1";
        Double price = 10.2;

        Product product = new Product(name, description, price);
        productRepository.saveAndFlush(product);

        Product p = productRepository.findByName(name).get();
        Assertions.assertThat(product).isEqualToComparingFieldByField(p);
    }
}