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

    private static InetAddress ip;

    static {
        try {
            ip = InetAddress.getLocalHost();
        } catch (Exception e) {
            LOGGER.error("There was an error trying to execute the operation", e);
            throw new RuntimeException("There was an error", e);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String get() {
        String host = "1 - HostName:" + ip.getHostName() + " - CanonicalHostName:" + ip.getCanonicalHostName() + " - HostAddress:" + ip.getHostAddress();
        LOGGER.info(host);
        return host;
    }

}
