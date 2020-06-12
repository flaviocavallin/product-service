package com.example.productservice.controller;

import com.example.productservice.IntegrationBaseTest;
import com.example.productservice.dto.BuyerDTO;
import com.example.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
public class BuyerControllerTest extends IntegrationBaseTest {

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
    public void create_buyer() throws Exception {
        String email = "email@email.com";
        String name = "name1";
        String surname = "surname1";

        BuyerDTO dto = createBuyer(email, name, surname);

        mockMvc.perform(get("/api/v1/buyer/id/{buyer_id}", dto.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(dto.getId())))
                .andExpect(jsonPath("$.email", is(dto.getEmail())))
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.surname", is(dto.getSurname())))
                .andDo(print());
    }

    @Test
    public void get_list_buyers() throws Exception {
        String email = "email@email.com";
        String name = "name1";
        String surname = "surname1";

        BuyerDTO dto = createBuyer(email, name, surname);

        mockMvc.perform(get("/api/v1/buyer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.elements[0].id", is(dto.getId())))
                .andExpect(jsonPath("$.elements[0].email", is(dto.getEmail())))
                .andExpect(jsonPath("$.elements[0].name", is(dto.getName())))
                .andExpect(jsonPath("$.elements[0].surname", is(dto.getSurname())))
                .andDo(print());
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
}
