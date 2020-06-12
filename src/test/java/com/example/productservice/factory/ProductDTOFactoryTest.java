package com.example.productservice.factory;

import com.example.productservice.domain.Product;
import com.example.productservice.dto.ProductDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductDTOFactoryTest {

    private ProductDTOFactory productDTOFactory;

    @BeforeEach
    public void setUp() {
        this.productDTOFactory = new ProductDTOFactory();
    }

    @Test
    public void get_buyerDTO_from_buyer_entity() {
        Product product = new Product("product1", "productDescription1", 10.2);
        ProductDTO dto = productDTOFactory.fromEntity(product);

        Assertions.assertThat(dto.getId()).isEqualTo(product.getId());
        Assertions.assertThat(dto.getName()).isEqualTo(product.getName());
        Assertions.assertThat(dto.getDescription()).isEqualTo(product.getDescription());
        Assertions.assertThat(dto.getPrice()).isEqualTo(product.getPrice());
    }
}
