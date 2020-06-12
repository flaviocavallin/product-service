package com.example.productservice.factory;

import com.example.productservice.domain.Item;
import com.example.productservice.dto.ItemDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemDTOFactory {

    ItemDTOFactory() {
        //do nothing
    }

    public ItemDTO fromEntity(Item item) {
        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setPrice(item.getPrice());
        dto.setProductId(item.getProduct().getId());
        return dto;
    }
}