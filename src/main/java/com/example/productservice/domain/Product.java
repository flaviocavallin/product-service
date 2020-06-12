package com.example.productservice.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product extends AbstractEntity<Integer> {

    private static final long serialVersionUID = -8990226958452772812L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name", length = 20, unique = true, nullable = false)
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Positive(message = "Price should be greater than zero")
    @Column(name = "price", nullable = false)
    private Double price;

    @Version
    @Column(nullable = false)
    private Integer version;

    Product() {
        // do nothing for hydration purpose
    }

    public Product(String name, String description, Double price) {
        this.name = Objects.requireNonNull(name, "name can not be null");
        this.description = Objects.requireNonNull(description, "description can not be null");
        this.price = Objects.requireNonNull(price, "price can not be null");
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name can not be null");
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNull(description, "description can not be null");
    }

    public void setPrice(Double price) {
        this.price = Objects.requireNonNull(price, "price can not be null");;
    }
}
