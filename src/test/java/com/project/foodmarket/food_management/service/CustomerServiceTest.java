package com.project.foodmarket.food_management.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import com.project.foodmarket.food_management.constants.CustomerConstants;
import com.project.foodmarket.food_management.document.Customer;
import com.project.foodmarket.food_management.model.CustomerRegisterRequest;
import com.project.foodmarket.food_management.model.WebResponse;
import com.project.foodmarket.food_management.repository.CustomerRepository;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CustomerServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
    }

    @Test
    void registerBadRequestTest() throws Exception {
        CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest();

        mockMvc.perform(post(CustomerConstants.REGISTER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRegisterRequest))).andExpectAll(
                        status().isBadRequest())
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<WebResponse<String>>() {
                            });

                    assertNotNull(response.getError());
                });
    }

    @Test
    void registerNotEmailFormatTest() throws Exception {
        CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest();
        customerRegisterRequest.setEmail("test2gmail.com");
        customerRegisterRequest.setUsername("test8252ad");
        customerRegisterRequest.setPassword("hello41223");
        customerRegisterRequest.setName("world");

        mockMvc.perform(post(CustomerConstants.REGISTER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRegisterRequest))).andExpectAll(
                        status().isBadRequest())
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<WebResponse<String>>() {
                            });                    
                    assertNotNull(response.getError());
                });
    }

    @Test
    void registerDuplicateUsernameTest() throws Exception {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setEmail("helo@gmail.com");
        customer.setPassword("7432gdsgsfav");
        customer.setUsername("test8252ad");
        customer.setName("hello");
        customerRepository.save(customer);

        CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest();
        customerRegisterRequest.setEmail("test2@gmail.com");
        customerRegisterRequest.setUsername("test8252ad");
        customerRegisterRequest.setPassword("hello41223");
        customerRegisterRequest.setName("world");

        mockMvc.perform(post(CustomerConstants.REGISTER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRegisterRequest))).andExpectAll(
                        status().isBadRequest())
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<WebResponse<String>>() {
                            });

                    assertNotNull(response.getError());
                });
    }

    @Test
    void registerSuccessTest() throws Exception {
        CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest();
        customerRegisterRequest.setEmail("test2@gmail.com");
        customerRegisterRequest.setUsername("test8252ad");
        customerRegisterRequest.setPassword("hello41223");
        customerRegisterRequest.setName("world");

        mockMvc.perform(post(CustomerConstants.REGISTER_PATH).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRegisterRequest)))
                .andExpectAll(status().isOk()).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<WebResponse<String>>() {
                            });

                    assertNull(response.getError());
                    assertEquals("OK", response.getData());
                });
    }
}
