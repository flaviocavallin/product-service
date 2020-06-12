package com.example.productservice.controller;

import com.example.productservice.dto.BuyerDTO;
import com.example.productservice.dto.PageDTO;
import com.example.productservice.service.BuyerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/buyer")
@Tag(name = "buyer", description = "Buyer API")
@OpenAPIDefinition(info = @Info(title = "Buyer API", version = "1.0"), tags = @Tag(name = "buyer"))
public class BuyerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyerController.class);
    private static final String PAGE_DEFAULT_SIZE = "50";
    private static final String PAGE_DEFAULT_NUMBER = "0";

    private BuyerService buyerService;

    BuyerController(BuyerService buyerService) {
        this.buyerService = Objects.requireNonNull(buyerService, "buyerService can not be null");
    }

    @Operation(summary = "Create a new buyer", description = "", tags = {"document"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BuyerDTO createBuyer(@Valid @RequestBody BuyerDTO buyerDTO) {
        return buyerService.create(buyerDTO.getEmail(), buyerDTO.getName(), buyerDTO.getSurname());
    }


    @Operation(summary = "Get buyer by the given id", description = "", tags = {"document"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Buyer not found")})
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BuyerDTO getBuyerById(@PathVariable(value = "id") int id) {
        return buyerService.getById(id);
    }


    @Operation(summary = "Get list of buyers", description = "", tags = {"document"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Buyer not found")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDTO<BuyerDTO> getAll(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_NUMBER, required = false) Integer page,
                                    @RequestParam(value = "size", defaultValue = PAGE_DEFAULT_SIZE, required = false) Integer size) {
        return buyerService.getAll(page, size);
    }
}
