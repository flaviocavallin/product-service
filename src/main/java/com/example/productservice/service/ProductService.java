package com.example.productservice.service;

import com.example.productservice.domain.Product;
import com.example.productservice.dto.PageDTO;
import com.example.productservice.dto.ProductDTO;
import com.example.productservice.exception.EntityNotFoundException;
import com.example.productservice.factory.ProductDTOFactory;
import com.example.productservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private ProductDTOFactory productDTOFactory;

    ProductService(ProductRepository productRepository, ProductDTOFactory productDTOFactory) {
        this.productRepository = Objects.requireNonNull(productRepository, "productRepository can not be null");
        this.productDTOFactory = Objects.requireNonNull(productDTOFactory, "productDTOFactory can not be null");
    }

    @Transactional(readOnly = false)
    public ProductDTO create(String name, String description, double price) {
        Product product = productRepository.save(new Product(name, description, price));
        return productDTOFactory.fromEntity(product);
    }

    @Transactional(readOnly = true)
    public ProductDTO getByName(String name) {
        Product product = productRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Product not found with name=" + name));
        return productDTOFactory.fromEntity(product);
    }

    @Transactional(readOnly = true)
    public PageDTO<ProductDTO> getAll(int page, int size) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, size));

        List<Product> products = productPage.getContent();
        if (CollectionUtils.isEmpty(products)) {
            return new PageDTO<>(Collections.emptyList(), 0, 0);
        }

        List<ProductDTO> dtos = new ArrayList<>(products.size());
        for (Product product : products) {
            dtos.add(productDTOFactory.fromEntity(product));
        }
        return new PageDTO<>(dtos, productPage.getTotalElements(), productPage.getTotalPages());
    }

    @Transactional(readOnly = false)
    public ProductDTO update(int id, String name, String description, Double price) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id=" + id));

        if (!StringUtils.isEmpty(name)) {
            product.setName(name);
        }

        if (!StringUtils.isEmpty(description)) {
            product.setDescription(description);
        }

        if (!Objects.isNull(price)) {
            product.setPrice(price);
        }

        return productDTOFactory.fromEntity(productRepository.save(product));
    }
}