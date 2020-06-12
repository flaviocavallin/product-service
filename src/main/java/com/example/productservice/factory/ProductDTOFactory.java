package com.example.productservice.factory;

import com.example.productservice.domain.Product;
import com.example.productservice.dto.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductDTOFactory {

    ProductDTOFactory() {
        //do nothing
    }

    public ProductDTO fromEntity(Product product) {
        Objects.requireNonNull(product, "product can not be null");
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        return dto;
    }
}
