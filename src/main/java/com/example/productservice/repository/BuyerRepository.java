package com.example.productservice.repository;

import com.example.productservice.domain.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer,Integer> {

    Optional<Buyer> findByEmail(String email);
}
