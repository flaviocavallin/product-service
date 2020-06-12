package com.example.productservice.service;

import com.example.productservice.domain.Buyer;
import com.example.productservice.dto.BuyerDTO;
import com.example.productservice.dto.PageDTO;
import com.example.productservice.exception.EntityNotFoundException;
import com.example.productservice.factory.BuyerDTOFactory;
import com.example.productservice.repository.BuyerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class BuyerService {

    private BuyerRepository buyerRepository;
    private BuyerDTOFactory buyerDTOFactory;

    BuyerService(BuyerRepository buyerRepository, BuyerDTOFactory buyerDTOFactory) {
        this.buyerRepository = Objects.requireNonNull(buyerRepository, "buyerRepository can not be null");
        this.buyerDTOFactory = Objects.requireNonNull(buyerDTOFactory, "buyerDTOFactory can not be null");
    }

    @Transactional(readOnly = false)
    public BuyerDTO create(String email, String name, String surname) {
        Buyer buyer = buyerRepository.save(new Buyer(email, name, surname));
        return buyerDTOFactory.fromEntity(buyer);
    }

    @Transactional(readOnly = true)
    public BuyerDTO getById(int id) {
        Buyer buyer = buyerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Buyer not found with id=" + id));
        return buyerDTOFactory.fromEntity(buyer);
    }

    @Transactional(readOnly = true)
    public PageDTO<BuyerDTO> getAll(int page, int size) {
        Page<Buyer> productPage = buyerRepository.findAll(PageRequest.of(page, size));

        List<Buyer> buyers = productPage.getContent();
        if (CollectionUtils.isEmpty(buyers)) {
            return new PageDTO<>(Collections.emptyList(), 0, 0);
        }

        List<BuyerDTO> dtos = new ArrayList<>(buyers.size());
        for (Buyer buyer : buyers) {
            dtos.add(buyerDTOFactory.fromEntity(buyer));
        }
        return new PageDTO<>(dtos, productPage.getTotalElements(), productPage.getTotalPages());
    }
}
