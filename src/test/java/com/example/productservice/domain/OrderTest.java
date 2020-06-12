package com.example.productservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderTest {

    private Buyer buyer;
    private Product product1, product2;

    @BeforeEach
    public void setUp() {
        buyer = new Buyer("email1@email.com", "name1", "surname1");
        product1 = new Product("product1", "description1", 10.2);
        product2 = new Product("product2", "description2", 10.1);
    }

    @Test
    public void given_order_with_items_get_total_value() {
        Order order = new Order(buyer);

        Assertions.assertThat(order.getOrderValue()).isEqualTo(0);

        order.addItem(new Item(product1));
        order.addItem(new Item(product2));

        Assertions.assertThat(order.getOrderValue()).isEqualTo(20.3);
    }
}
