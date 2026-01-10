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

                                        Customer customerDb = customerRepository.findByEmail("test@gmail.com")
                                                        .orElse(null);

                                        assertNotNull(customerDb);
                                        assertEquals(customerDb.getToken(), response.getData().getToken());
                                });
        }

        @Test
        void logoutFailed() throws Exception {
                mockMvc.perform(delete(CustomerConstants.LOGOUT_PATH)
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpectAll(
                                                status().isUnauthorized())
                                .andDo(result -> {
                                        WebResponse<TokenResponse> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<TokenResponse>>() {
                                                        });
                                        assertNotNull(response.getError());
                                });
        }

        @Test
        void logoutSuccess() throws Exception {                
                Customer customer = new Customer();
                customer.setId("test26138f");
                customer.setEmail("test@gmail.com");
                customer.setUsername("testhello");
                customer.setPassword(BCrypt.hashpw("world", BCrypt.gensalt()));
                customer.setName("test2");
                customer.setToken(
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjIxMTRzZmFhIiwiaXNzdWVyIjoiZm9vZC1tYXJrZXQiLCJzdWJqZWN0IjoidGVzdGhlbGxvIn0.uqcz2KXo52I_M2zp7yd9V8dyluifrG8yPNBs-2VeUnw");
                customer.setTokenExpiredAt(System.currentTimeMillis() + 100000000L);
                customerRepository.save(customer);

                mockMvc.perform(
                                delete(CustomerConstants.LOGOUT_PATH)
                                                .header("Authorization", "Bearer " + customer.getToken())
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpectAll(
                                                status().isOk())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper
                                                        .readValue(result.getResponse().getContentAsString(),
                                                                        new TypeReference<WebResponse<String>>() {
                                                                        });
                                        assertNull(response.getError());
                                        assertEquals("OK", response.getData());

                                        Customer customerDb = customerRepository.findByEmail("test@gmail.com")
                                                        .orElse(null);

                                        assertNotNull(customerDb);
                                        assertNull(customerDb.getToken());
                                        assertEquals(0, customerDb.getTokenExpiredAt());
                                });
        }
}
