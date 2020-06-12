package com.example.productservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/health")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<Void> health() {
        //TODO improve this
        return ResponseEntity.ok().build();
    }
}
