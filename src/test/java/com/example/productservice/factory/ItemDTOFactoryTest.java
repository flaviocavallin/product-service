package com.example.productservice.factory;

import com.example.productservice.domain.Buyer;
import com.example.productservice.domain.Item;
import com.example.productservice.domain.Product;
import com.example.productservice.dto.ItemDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemDTOFactoryTest {

    private ItemDTOFactory itemDTOFactory;
    private Product product;
    private Buyer buyer;

    @BeforeEach
    public void setUp() {
        this.itemDTOFactory = new ItemDTOFactory();
        this.product = new Product("product1", "description1", 10.2);
        this.buyer = new Buyer("email1@mail.com", "name1", "surname1");
    }

    @Test
    public void get_itemDTO_from_item_entity() {
        Item item = new Item(product);
        ItemDTO dto = itemDTOFactory.fromEntity(item);

        Assertions.assertThat(dto.getId()).isEqualTo(item.getId());
        Assertions.assertThat(dto.getProductId()).isEqualTo(item.getProduct().getId());
        Assertions.assertThat(dto.getPrice()).isEqualTo(item.getProduct().getPrice());
    }
}
