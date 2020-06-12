package com.example.productservice.factory;

import com.example.productservice.domain.Buyer;
import com.example.productservice.dto.BuyerDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuyerDTOFactoryTest {

    private BuyerDTOFactory buyerDTOFactory;

    @BeforeEach
    public void setUp() {
        this.buyerDTOFactory = new BuyerDTOFactory();
    }

    @Test
    public void get_buyerDTO_from_buyer_entity() {
        Buyer buyer = new Buyer("email1@mail.com", "name1", "surname1");
        BuyerDTO dto = buyerDTOFactory.fromEntity(buyer);

        Assertions.assertThat(dto.getId()).isEqualTo(buyer.getId());
        Assertions.assertThat(dto.getEmail()).isEqualTo(buyer.getEmail());
        Assertions.assertThat(dto.getName()).isEqualTo(buyer.getName());
        Assertions.assertThat(dto.getSurname()).isEqualTo(buyer.getSurname());
    }
}
