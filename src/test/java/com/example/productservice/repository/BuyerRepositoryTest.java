package com.example.productservice.repository;

import com.example.productservice.IntegrationBaseTest;
import com.example.productservice.domain.Buyer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BuyerRepositoryTest extends IntegrationBaseTest {

    @Autowired
    private BuyerRepository buyerRepository;

    @Test
    public void given_existing_buyer_find_by_email() {
        String email = "email1@mail.com";
        String name = "name1";
        String surname = "surname1";

        Buyer buyer = new Buyer(email, name, surname);
        buyerRepository.saveAndFlush(buyer);

        Buyer b = buyerRepository.findByEmail(email).get();
        Assertions.assertThat(buyer).isEqualToComparingFieldByField(b);
    }
}