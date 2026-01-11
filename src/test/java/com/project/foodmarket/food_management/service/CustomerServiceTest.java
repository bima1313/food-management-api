package com.project.foodmarket.food_management.service;

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
import com.project.foodmarket.food_management.model.WebResponse;
import com.project.foodmarket.food_management.model.customer.CustomerRegisterRequest;
import com.project.foodmarket.food_management.model.customer.CustomerResponse;
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

                mockMvc.perform(post(CustomerConstants.BASE_PATH)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(customerRegisterRequest))).andExpectAll(
                                                status().isBadRequest())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
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

                mockMvc.perform(post(CustomerConstants.BASE_PATH)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(customerRegisterRequest))).andExpectAll(
                                                status().isBadRequest())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
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
                customer.setPassword(BCrypt.hashpw("7432gdsgsfav", BCrypt.gensalt()));
                customer.setUsername("test8252ad");
                customer.setName("hello");
                customerRepository.save(customer);

                CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest();
                customerRegisterRequest.setEmail("test2@gmail.com");
                customerRegisterRequest.setUsername("test8252ad");
                customerRegisterRequest.setPassword("hello41223");
                customerRegisterRequest.setName("world");

                mockMvc.perform(post(CustomerConstants.BASE_PATH)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(customerRegisterRequest))).andExpectAll(
                                                status().isBadRequest())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
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
                customerRegisterRequest.setPassword(BCrypt.hashpw("hello41223", BCrypt.gensalt()));
                customerRegisterRequest.setName("world");

                mockMvc.perform(post(CustomerConstants.BASE_PATH).accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(customerRegisterRequest)))
                                .andExpectAll(status().isOk()).andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<String>>() {
                                                        });

                                        assertNull(response.getError());
                                        assertEquals("OK", response.getData());
                                });
        }

        @Test
        void getUserUnauthorized() throws Exception {
                String id = "test26138f";
                mockMvc.perform(get(CustomerConstants.BASE_PATH + "/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization",
                                                "not found"))
                                .andExpectAll(status().isUnauthorized())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<String>>() {
                                                        });

                                        assertNotNull(response.getError());
                                });
        }

        @Test
        void getUserUnauthorizedTokenNotSend() throws Exception {
                String id = "test26138f";
                mockMvc.perform(get(CustomerConstants.BASE_PATH + "/{id}", id)
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpectAll(status().isUnauthorized())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<String>>() {
                                                        });

                                        assertNotNull(response.getError());
                                });
        }

        @Test
        void getUserSuccess() throws Exception {
                String id = "test26138f";
                Customer customer = new Customer();
                customer.setId(id);
                customer.setUsername("testhello");
                customer.setPassword(BCrypt.hashpw("world", BCrypt.gensalt()));
                customer.setName("test2");
                customer.setToken(
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjIxMTRzZmFhIiwiaXNzdWVyIjoiZm9vZC1tYXJrZXQiLCJzdWJqZWN0IjoidGVzdGhlbGxvIn0.uqcz2KXo52I_M2zp7yd9V8dyluifrG8yPNBs-2VeUnw");
                customer.setTokenExpiredAt(System.currentTimeMillis() + 100000000L);
                customerRepository.save(customer);

                mockMvc.perform(get(CustomerConstants.BASE_PATH + "/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + customer.getToken()))
                                .andExpectAll(status().isOk())
                                .andDo(result -> {
                                        WebResponse<CustomerResponse> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<CustomerResponse>>() {
                                                        });

                                        assertNull(response.getError());
                                        assertEquals("testhello", response.getData().getUsername());
                                        assertEquals("test2", response.getData().getName());
                                });
        }

        @Test
        void getTokenUserExpired() throws Exception {
                String id = "test26138f";
                Customer customer = new Customer();
                customer.setId(id);
                customer.setUsername("testhello");
                customer.setPassword(BCrypt.hashpw("world", BCrypt.gensalt()));
                customer.setName("test2");
                customer.setToken(
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjIxMTRzZmFhIiwiaXNzdWVyIjoiZm9vZC1tYXJrZXQiLCJzdWJqZWN0IjoidGVzdGhlbGxvIn0.uqcz2KXo52I_M2zp7yd9V8dyluifrG8yPNBs-2VeUnw");
                customer.setTokenExpiredAt(System.currentTimeMillis() - 100000000L);
                customerRepository.save(customer);

                mockMvc.perform(get(CustomerConstants.BASE_PATH + "/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization","Bearer " + customer.getToken()))
                                .andExpectAll(status().isUnauthorized())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<String>>() {
                                                        });

                                        assertNotNull(response.getError());
                                });
        }

}
