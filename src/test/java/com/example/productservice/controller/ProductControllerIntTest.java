package com.example.productservice.controller;

import com.example.productservice.IntegrationBaseTest;
import com.example.productservice.domain.Product;
import com.example.productservice.dto.ProductDTO;
import com.example.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
public class ProductControllerIntTest extends IntegrationBaseTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void get_product_by_name() throws Exception {
        String productName = "product1";
        Product product = new Product(productName, "product description1", 10.2);
        productRepository.save(product);

        mockMvc.perform(get("/api/v1/product/name/{name}", productName)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(product.getId())))
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.description", is(product.getDescription())))
                .andExpect(jsonPath("$.price", is(product.getPrice())))
                .andDo(print());
    }

    @Test
    public void given_productName_tryGetProductByName_and_throw_product_not_found() throws Exception {
        String productName = "product1";
        mockMvc.perform(get("/api/v1/product/name/{name}", productName)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", is("Product not found with name=" + productName)))
                .andDo(print());
    }

    @Test
    public void create_product() throws Exception {
        String name = "product123";
        String description = "desc product 123";
        double price = 11.1;

        createProduct(name, description, price);

        mockMvc.perform(get("/api/v1/product/name/{name}", name)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.description", is(description)))
                .andExpect(jsonPath("$.price", is(price)))
                .andDo(print());
    }

    @Test
    public void given_product_update_it() throws Exception {
        String name = "product123";
        String description = "desc product 123";
        double price = 11.1;

        ProductDTO productDTO = createProduct(name, description, price);

        ProductDTO updateDTO = new ProductDTO();
        updateDTO.setName("new name 123");
        updateDTO.setDescription("new description 123");
        updateDTO.setPrice(10.1);

        String content = objectMapper.writeValueAsString(updateDTO);
        mockMvc.perform(put("/api/v1/product/id/{id}", productDTO.getId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(productDTO.getId())))
                .andExpect(jsonPath("$.name", is(updateDTO.getName())))
                .andExpect(jsonPath("$.description", is(updateDTO.getDescription())))
                .andExpect(jsonPath("$.price", is(updateDTO.getPrice())))
                .andDo(print());

    }


    @Test
    public void given_productId_tryUpdateProduct_and_throw_product_not_found() throws Exception {
        int productId = 1;
        ProductDTO updateDTO = new ProductDTO();
        updateDTO.setName("new name 123");
        updateDTO.setDescription("new description 123");
        updateDTO.setPrice(10.1);

        String content = objectMapper.writeValueAsString(updateDTO);

        mockMvc.perform(put("/api/v1/product/id/{id}", productId)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", is("Product not found with id=" + productId)))
                .andDo(print());
    }

    @Test
    public void get_list_products() throws Exception {
        String name = "product123";
        String description = "desc product 123";
        double price = 11.1;

        ProductDTO productDTO = createProduct(name, description, price);

        mockMvc.perform(get("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.elements[0].id", is(productDTO.getId())))
                .andExpect(jsonPath("$.elements[0].name", is(productDTO.getName())))
                .andExpect(jsonPath("$.elements[0].description", is(productDTO.getDescription())))
                .andExpect(jsonPath("$.elements[0].price", is(productDTO.getPrice())))
                .andDo(print());
    }

    private ProductDTO createProduct(String name, String description, double price) throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setDescription(description);
        productDTO.setPrice(price);

        String content = objectMapper.writeValueAsString(productDTO);

        MvcResult result = mockMvc.perform(post("/api/v1/product")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name", is(productDTO.getName())))
                .andExpect(jsonPath("$.description", is(productDTO.getDescription())))
                .andExpect(jsonPath("$.price", is(productDTO.getPrice())))
                .andDo(print()).andReturn();
        String resultContent = result.getResponse().getContentAsString();


        return objectMapper.readValue(resultContent, ProductDTO.class);
    }


}
