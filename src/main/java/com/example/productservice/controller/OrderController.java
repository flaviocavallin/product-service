package com.example.productservice.controller;

import com.example.productservice.dto.OrderDTO;
import com.example.productservice.dto.PageDTO;
import com.example.productservice.service.OrderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/order")
@Tag(name = "order", description = "Order API")
@OpenAPIDefinition(info = @Info(title = "Order API", version = "1.0"), tags = @Tag(name = "order"))
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private static final String PAGE_DEFAULT_SIZE = "50";
    private static final String PAGE_DEFAULT_NUMBER = "0";

    private OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = Objects.requireNonNull(orderService, "orderService can not be null");
    }

    @Operation(summary = "Create an Order", description = "", tags = {"document"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDTO createOrder(@RequestParam(value = "buyerEmail") String buyerEmail) {
        return orderService.createOrder(buyerEmail);
    }

    @Operation(summary = "Get an Order by the given id", description = "", tags = {"document"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Order not found")})
    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDTO getOrderById(@PathVariable(value = "orderId") long orderId) {
        return orderService.getOrderById(orderId);
    }

    @Operation(summary = "Add a product to an existing order", description = "", tags = {"document"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Order/Product not found")})
    @PostMapping(value = "/{orderId}/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDTO addProduct(@PathVariable(value = "orderId") Long orderId, @PathVariable(value = "productId") Integer productId) {
        return orderService.addProductToOrder(orderId, productId);
    }

    @Operation(summary = "Get list a orders by the given dates", description = "", tags = {"document"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDTO<OrderDTO> getOrders(@RequestParam(value = "dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
                                       @RequestParam(value = "dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo,
                                       @RequestParam(value = "page", defaultValue = PAGE_DEFAULT_NUMBER, required = false) Integer page,
                                       @RequestParam(value = "size", defaultValue = PAGE_DEFAULT_SIZE, required = false) Integer size) {
        return orderService.getOrders(dateFrom, dateTo, page, size);
    }
}
