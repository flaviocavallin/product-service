package com.example.productservice.controller;

import com.example.productservice.dto.PageDTO;
import com.example.productservice.dto.ProductDTO;
import com.example.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping(value = "/api/v1/product")
@Tag(name = "product", description = "Product API")
@OpenAPIDefinition(info = @Info(title = "Product API", version = "1.0"), tags = @Tag(name = "product"))
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private static final String PAGE_DEFAULT_SIZE = "50";
    private static final String PAGE_DEFAULT_NUMBER = "0";

    private ProductService productService;

    ProductController(ProductService productService) {
        this.productService = Objects.requireNonNull(productService, "productService can not be null");
    }

    @Operation(summary = "Create product", description = "", tags = {"document"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.create(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice());
    }

    @Operation(summary = "Get product by the given name", description = "", tags = {"document"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Product not found")})
    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO getProductByName(
            @Parameter(description = "Product name", required = true)
            @PathVariable(value = "name") String name) {
        return productService.getByName(name);
    }

    @Operation(summary = "Update product by the given id and params", description = "", tags = {"document"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Product not found")})
    @PutMapping(value = "/id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO updateProduct(@PathVariable(value = "id") Integer id, @RequestBody ProductDTO productDTO) {
        return productService.update(id, productDTO.getName(), productDTO.getDescription(), productDTO.getPrice());
    }

    @Operation(summary = "Get list of products", description = "", tags = {"document"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDTO<ProductDTO> getAll(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_NUMBER, required = false) Integer page,
                                      @RequestParam(value = "size", defaultValue = PAGE_DEFAULT_SIZE, required = false) Integer size) {
        return productService.getAll(page, size);
    }
}
