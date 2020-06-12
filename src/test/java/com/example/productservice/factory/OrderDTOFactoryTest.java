package com.example.productservice.factory;

import com.example.productservice.domain.Buyer;
import com.example.productservice.domain.Item;
import com.example.productservice.domain.Order;
import com.example.productservice.domain.Product;
import com.example.productservice.dto.BuyerDTO;
import com.example.productservice.dto.ItemDTO;
import com.example.productservice.dto.OrderDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderDTOFactoryTest {

    private OrderDTOFactory orderDTOFactory;

    private Order order;
    private Buyer buyer;
    private Product product;
    private Item item;

    @BeforeEach
    public void setUp() {
        this.orderDTOFactory = new OrderDTOFactory(new BuyerDTOFactory(), new ItemDTOFactory());
        this.product = new Product("product1", "description1", 10.2);
        this.buyer = new Buyer("email1@mail.com", "name1", "surname1");
        this.order = new Order(buyer);
        this.item = new Item(product);
        this.order.addItem(item);
    }

    @Test
    public void get_orderDTO_from_order_entity() {
        OrderDTO dto = orderDTOFactory.fromEntity(order);

        Assertions.assertThat(dto.getId()).isEqualTo(order.getId());
        Assertions.assertThat(dto.getTotalValue()).isEqualTo(order.getOrderValue());

        Assertions.assertThat(dto.getItems()).size().isEqualTo(1);

        ItemDTO itemDTO = dto.getItems().get(0);
        Assertions.assertThat(itemDTO.getId()).isEqualTo(item.getId());
        Assertions.assertThat(itemDTO.getProductId()).isEqualTo(item.getProduct().getId());
        Assertions.assertThat(itemDTO.getPrice()).isEqualTo(item.getProduct().getPrice());

        BuyerDTO buyerDTO = dto.getBuyer();
        Assertions.assertThat(buyerDTO.getId()).isEqualTo(buyer.getId());
        Assertions.assertThat(buyerDTO.getEmail()).isEqualTo(buyer.getEmail());
        Assertions.assertThat(buyerDTO.getName()).isEqualTo(buyer.getName());
        Assertions.assertThat(buyerDTO.getSurname()).isEqualTo(buyer.getSurname());
    }
}
