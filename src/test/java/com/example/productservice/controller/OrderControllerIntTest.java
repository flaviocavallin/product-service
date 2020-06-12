package com.example.productservice.controller;

import com.example.productservice.IntegrationBaseTest;
import com.example.productservice.dto.BuyerDTO;
import com.example.productservice.dto.OrderDTO;
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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
public class OrderControllerIntTest extends IntegrationBaseTest {
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
    public void given_existing_buyer_email_create_order() throws Exception {
        String buyerEmail = "email1@mail.com";
        BuyerDTO buyerDTO = createBuyer(buyerEmail, "name1", "surname1");

        OrderDTO orderDTO = createOrder(buyerEmail);

        mockMvc.perform(get("/api/v1/order/{orderId}", orderDTO.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(orderDTO.getId().intValue())))
                .andExpect(jsonPath("$.issueDate", is(orderDTO.getIssueDate().toString())))
                .andExpect(jsonPath("$.buyer.email", is(buyerEmail)))
                .andDo(print());
    }

    @Test
    public void given_existing_order_add_product() throws Exception {
        String buyerEmail = "email1@mail.com";
        BuyerDTO buyerDTO = createBuyer(buyerEmail, "name1", "surname1");
        OrderDTO orderDTO = createOrder(buyerEmail);
        ProductDTO productDTO = createProduct("product1", "descr prod1", 10.1);

        mockMvc.perform(post("/api/v1/order/{orderId}/product/{productId}", orderDTO.getId(), productDTO.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(orderDTO.getId().intValue())))
                .andExpect(jsonPath("$.items[0].productId", is(productDTO.getId())))
                .andExpect(jsonPath("$.items[0].price", is(productDTO.getPrice())))
                .andDo(print());
    }

    @Test
    public void given_orderId_tryAddProduct_to_unexisting_orderId_then_throw_order_not_found() throws Exception {
        mockMvc.perform(post("/api/v1/order/{orderId}/product/{productId}", 1, 11)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", is("Order not found for id=" + 1)))
                .andDo(print());
    }

    @Test
    public void get_list_orders_by_given_dates() throws Exception {
        String buyerEmail = "email1@mail.com";
        BuyerDTO buyerDTO = createBuyer(buyerEmail, "name1", "surname1");
        OrderDTO orderDTO = createOrder(buyerEmail);

        Date dateFrom = Date.from(orderDTO.getIssueDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = formatter.format(dateFrom);

        mockMvc.perform(get("/api/v1/order")
                .param("dateFrom", todayDate)
                .param("dateTo", todayDate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.elements[0].id", is(orderDTO.getId().intValue())))
                .andDo(print());
    }

    private OrderDTO createOrder(String buyerEmail) throws Exception {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = formatter.format(new Date());

        MvcResult result = mockMvc.perform(post("/api/v1/order").param("buyerEmail", buyerEmail)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.buyer.email", is(buyerEmail)))
                .andExpect(jsonPath("$.issueDate", is(todayDate)))
                .andDo(print()).andReturn();
        String resultContent = result.getResponse().getContentAsString();

        return objectMapper.readValue(resultContent, OrderDTO.class);
    }

    private BuyerDTO createBuyer(String email, String name, String surname) throws Exception {
        BuyerDTO buyerDTO = new BuyerDTO();
        buyerDTO.setEmail(email);
        buyerDTO.setName(name);
        buyerDTO.setSurname(surname);

        String content = objectMapper.writeValueAsString(buyerDTO);

        MvcResult result = mockMvc.perform(post("/api/v1/buyer")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.email", is(buyerDTO.getEmail())))
                .andExpect(jsonPath("$.name", is(buyerDTO.getName())))
                .andExpect(jsonPath("$.surname", is(buyerDTO.getSurname())))
                .andDo(print()).andReturn();
        String resultContent = result.getResponse().getContentAsString();

        return objectMapper.readValue(resultContent, BuyerDTO.class);
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
