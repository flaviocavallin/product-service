package com.example.productservice.repository;

import com.example.productservice.IntegrationBaseTest;
import com.example.productservice.domain.Buyer;
import com.example.productservice.domain.Item;
import com.example.productservice.domain.Order;
import com.example.productservice.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class OrderRepositoryTest extends IntegrationBaseTest {

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Product product;
    private Buyer buyer;

    @BeforeEach
    public void setUp() {
        String email = "email1@mail.com";
        String name = "name1";
        String surname = "surname1";
        this.buyer = buyerRepository.saveAndFlush(new Buyer(email, name, surname));

        String productName = "product1";
        String description = "description product1";
        Double price = 10.2;

        this.product = productRepository.saveAndFlush(new Product(productName, description, price));
    }

    @Test
    public void given_existing_order_find_by_order_date_range() {
        Order order = new Order(buyer);
        Item item = new Item(product);
        orderRepository.saveAndFlush(order);

        //Date dateFrom = Date.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        //Date dateTo = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());

        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now().plusDays(1);

        Page<Order> pageOrders = orderRepository.findAllBetweenDates(dateFrom, dateTo, PageRequest.of(0, 100));
        List<Order> orders = pageOrders.getContent();
        Assertions.assertThat(orders).size().isEqualTo(1);
        Assertions.assertThat(orders).containsExactly(order);


        //Date dateToPlus1 = Date.from(LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toInstant());
        LocalDate dateToPlus1 = LocalDate.now().plusDays(2);

        pageOrders = orderRepository.findAllBetweenDates(dateTo, dateToPlus1, PageRequest.of(0, 100));
        orders = pageOrders.getContent();
        Assertions.assertThat(orders).isEmpty();
    }
}