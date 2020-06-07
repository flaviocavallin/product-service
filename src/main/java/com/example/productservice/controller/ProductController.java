package com.example.productservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RestController
@RequestMapping(value = "/api/v1/product")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String get() {
        String result = "Hello World!!!";
        LOGGER.info(result);
        return result;
    }

}
