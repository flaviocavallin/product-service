package com.example.productservice.factory;

import com.example.productservice.domain.Buyer;
import com.example.productservice.dto.BuyerDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BuyerDTOFactory {

    BuyerDTOFactory(){
        //do nothing
    }

    public BuyerDTO fromEntity(Buyer buyer){
        Objects.requireNonNull(buyer, "buyer can not be null");
        BuyerDTO dto = new BuyerDTO();
        dto.setId(buyer.getId());
        dto.setEmail(buyer.getEmail());
        dto.setName(buyer.getName());
        dto.setSurname(buyer.getSurname());
        return dto;
    }

}
