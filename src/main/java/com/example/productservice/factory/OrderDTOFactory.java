package com.example.productservice.factory;

import com.example.productservice.domain.Order;
import com.example.productservice.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderDTOFactory {

    private BuyerDTOFactory buyerDTOFactory;
    private ItemDTOFactory itemDTOFactory;

    @Autowired
    OrderDTOFactory(BuyerDTOFactory buyerDTOFactory, ItemDTOFactory itemDTOFactory) {
        this.buyerDTOFactory = Objects.requireNonNull(buyerDTOFactory, "buyerDTOFactory can not be null");
        this.itemDTOFactory = Objects.requireNonNull(itemDTOFactory, "itemDTOFactory can not be null");
    }

    public OrderDTO fromEntity(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setBuyer(buyerDTOFactory.fromEntity(order.getBuyer()));
        dto.setIssueDate(order.getIssueDate());

        order.getItems().forEach(item -> dto.addItem(itemDTOFactory.fromEntity(item)));

        dto.setTotalValue(order.getOrderValue());
        return dto;
    }

}
