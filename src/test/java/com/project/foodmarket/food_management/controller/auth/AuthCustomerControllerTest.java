package com.project.foodmarket.food_management.controller.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import com.project.foodmarket.food_management.constants.CustomerConstants;
import com.project.foodmarket.food_management.document.Customer;
import com.project.foodmarket.food_management.model.CustomerLoginRequest;
import com.project.foodmarket.food_management.model.CustomerResponse;
import com.project.foodmarket.food_management.model.TokenResponse;
import com.project.foodmarket.food_management.model.WebResponse;
import com.project.foodmarket.food_management.repository.CustomerRepository;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthCustomerControllerTest {

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
    void loginFailedUserNotFoundTest() throws Exception {
        CustomerLoginRequest customerLoginRequest = new CustomerLoginRequest();
        customerLoginRequest.setEmail("test@gmail.com");
        customerLoginRequest.setPassword("secretPassword");
        mockMvc.perform(post(CustomerConstants.LOGIN_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerLoginRequest)))
                .andExpectAll(
                        status().isUnauthorized())
                .andDo(result -> {
                    WebResponse<CustomerResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<WebResponse<CustomerResponse>>() {
                            });

                    assertNotNull(response.getError());
                });
    }

    @Test
    void loginFailedWrongPassword() throws Exception {
        Customer customer = new Customer();
        customer.setEmail("test@gmail.com");
        customer.setPassword(BCrypt.hashpw("hello_world2026", BCrypt.gensalt()));
        customer.setUsername("helloworld2026");
        customer.setName("world");
        customerRepository.save(customer);
        
        CustomerLoginRequest customerLoginRequest = new CustomerLoginRequest();
        customerLoginRequest.setEmail("test@gmail.com");
        customerLoginRequest.setPassword("secretPassword");
        mockMvc.perform(post(CustomerConstants.LOGIN_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerLoginRequest)))
                .andExpectAll(
                        status().isUnauthorized())
                .andDo(result -> {
                    WebResponse<CustomerResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<WebResponse<CustomerResponse>>() {
                            });

                    assertNotNull(response.getError());
                });
    }

    @Test
    void loginSuccess() throws Exception {    
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setEmail("test@gmail.com");
        customer.setUsername("helloworld2026");
        customer.setPassword(BCrypt.hashpw("secretPassword", BCrypt.gensalt()));
        customer.setName("world");
        customerRepository.save(customer);
        
        CustomerLoginRequest customerLoginRequest = new CustomerLoginRequest();
        customerLoginRequest.setEmail("test@gmail.com");
        customerLoginRequest.setPassword("secretPassword");
        
        mockMvc.perform(post(CustomerConstants.LOGIN_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerLoginRequest)))
                .andExpectAll(
                        status().isOk())
                .andDo(result -> {
                    WebResponse<TokenResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<WebResponse<TokenResponse>>() {
                            });

                    assertNull(response.getError());
                    assertNotNull(response.getData().getToken());

                    Customer customerDb = customerRepository.findByEmail("test@gmail.com").orElse(null);

                    assertNotNull(customerDb);
                    assertEquals(customerDb.getToken(), response.getData().getToken());
                });
    }
}
