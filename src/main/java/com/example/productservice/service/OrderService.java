package com.example.productservice.service;

import com.example.productservice.domain.Buyer;
import com.example.productservice.domain.Item;
import com.example.productservice.domain.Order;
import com.example.productservice.domain.Product;
import com.example.productservice.dto.OrderDTO;
import com.example.productservice.dto.PageDTO;
import com.example.productservice.exception.EntityNotFoundException;
import com.example.productservice.factory.OrderDTOFactory;
import com.example.productservice.repository.BuyerRepository;
import com.example.productservice.repository.OrderRepository;
import com.example.productservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private BuyerRepository buyerRepository;
    private ProductRepository productRepository;
    private OrderDTOFactory orderDTOFactory;

    OrderService(OrderRepository orderRepository, BuyerRepository buyerRepository, ProductRepository productRepository, OrderDTOFactory orderDTOFactory) {
        this.orderRepository = Objects.requireNonNull(orderRepository, "orderRepository can not be null");
        this.buyerRepository = Objects.requireNonNull(buyerRepository, "buyerRepository can not be null");
        this.productRepository = Objects.requireNonNull(productRepository, "productRepository can not be null");
        this.orderDTOFactory = Objects.requireNonNull(orderDTOFactory, "orderDTOFactory can not be null");
    }

    @Transactional(readOnly = false)
    public OrderDTO createOrder(String buyerEmail) {
        Buyer buyer = buyerRepository.findByEmail(buyerEmail)
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found for email=" + buyerEmail));

        Order order = orderRepository.save(new Order(buyer));

        return orderDTOFactory.fromEntity(order);
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for id=" + orderId));
        return orderDTOFactory.fromEntity(order);
    }

    @Transactional(readOnly = false)
    public OrderDTO addProductToOrder(long orderId, int productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found for id=" + orderId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found for id=" + productId));
        order.addItem(new Item(product));

        return orderDTOFactory.fromEntity(orderRepository.getOne(order.getId()));
    }

    @Transactional(readOnly = true)
    public PageDTO<OrderDTO> getOrders(LocalDate dateFrom, LocalDate dateTo, int page, int size) {

        Page<Order> pageOrders = orderRepository.findAllBetweenDates(dateFrom, dateTo, PageRequest.of(page, size));

        List<Order> orders = pageOrders.getContent();
        if (CollectionUtils.isEmpty(orders)) {
            return new PageDTO<>(Collections.emptyList(), 0, 0);
        }

        List<OrderDTO> dtos = new ArrayList<>(orders.size());
        for (Order order : orders) {
            dtos.add(orderDTOFactory.fromEntity(order));
        }
        return new PageDTO<>(dtos, pageOrders.getTotalElements(), pageOrders.getTotalPages());
    }
}
