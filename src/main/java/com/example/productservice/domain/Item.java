package com.example.productservice.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item extends AbstractEntity<Long> {

    private static final long serialVersionUID = -8990226958452772812L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;

    @Positive(message = "Price should be greater than zero")
    @Column(name = "price", nullable = false)
    private Double price;

    @Version
    @Column(nullable = false)
    private Long version;

    Item() {
        // do nothing for hydration purpose
    }

    public Item(Product product) {
        this.product = Objects.requireNonNull(product, "product can not be null");
        this.price = product.getPrice();
    }

    @Override
    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public Double getPrice() {
        return price;
    }

    public void setOrder(Order order) {
        this.order = Objects.requireNonNull(order, "order can not be null");
    }
}
