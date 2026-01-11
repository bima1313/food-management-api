package com.project.foodmarket.food_management.user.service;

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

import com.project.foodmarket.food_management.constants.UserConstants;
import com.project.foodmarket.food_management.document.User;
import com.project.foodmarket.food_management.model.WebResponse;
import com.project.foodmarket.food_management.model.user.UserRegisterRequest;
import com.project.foodmarket.food_management.model.user.UserResponse;
import com.project.foodmarket.food_management.repository.UserRepository;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserServiceTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        void setup() {
                userRepository.deleteAll();
        }

        @Test
        void registerBadRequestTest() throws Exception {
                UserRegisterRequest userRegisterRequest = new UserRegisterRequest();

                mockMvc.perform(post(UserConstants.BASE_PATH)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRegisterRequest))).andExpectAll(
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
                UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
                userRegisterRequest.setEmail("test2gmail.com");
                userRegisterRequest.setUsername("test8252ad");
                userRegisterRequest.setPassword("hello41223");
                userRegisterRequest.setName("world");

                mockMvc.perform(post(UserConstants.BASE_PATH)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRegisterRequest))).andExpectAll(
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
                User user = new User();
                user.setId(UUID.randomUUID().toString());
                user.setEmail("helo@gmail.com");
                user.setPassword(BCrypt.hashpw("7432gdsgsfav", BCrypt.gensalt()));
                user.setUsername("test8252ad");
                user.setName("hello");
                userRepository.save(user);

                UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
                userRegisterRequest.setEmail("test2@gmail.com");
                userRegisterRequest.setUsername("test8252ad");
                userRegisterRequest.setPassword("hello41223");
                userRegisterRequest.setName("world");

                mockMvc.perform(post(UserConstants.BASE_PATH)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRegisterRequest))).andExpectAll(
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
                UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
                userRegisterRequest.setEmail("test2@gmail.com");
                userRegisterRequest.setUsername("test8252ad");
                userRegisterRequest.setPassword(BCrypt.hashpw("hello41223", BCrypt.gensalt()));
                userRegisterRequest.setName("world");

                mockMvc.perform(post(UserConstants.BASE_PATH).accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRegisterRequest)))
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
                mockMvc.perform(get(UserConstants.BASE_PATH + "/{id}", id)
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
                mockMvc.perform(get(UserConstants.BASE_PATH + "/{id}", id)
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
                User user = new User();
                user.setId(id);
                user.setUsername("testhello");
                user.setPassword(BCrypt.hashpw("world", BCrypt.gensalt()));
                user.setName("test2");
                user.setToken(
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjIxMTRzZmFhIiwiaXNzdWVyIjoiZm9vZC1tYXJrZXQiLCJzdWJqZWN0IjoidGVzdGhlbGxvIn0.uqcz2KXo52I_M2zp7yd9V8dyluifrG8yPNBs-2VeUnw");
                user.setTokenExpiredAt(System.currentTimeMillis() + 100000000L);
                userRepository.save(user);

                mockMvc.perform(get(UserConstants.BASE_PATH + "/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + user.getToken()))
                                .andExpectAll(status().isOk())
                                .andDo(result -> {
                                        WebResponse<UserResponse> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<UserResponse>>() {
                                                        });

                                        assertNull(response.getError());
                                        assertEquals("testhello", response.getData().getUsername());
                                        assertEquals("test2", response.getData().getName());
                                });
        }

        @Test
        void getTokenUserExpired() throws Exception {
                String id = "test26138f";
                User user = new User();
                user.setId(id);
                user.setUsername("testhello");
                user.setPassword(BCrypt.hashpw("world", BCrypt.gensalt()));
                user.setName("test2");
                user.setToken(
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjIxMTRzZmFhIiwiaXNzdWVyIjoiZm9vZC1tYXJrZXQiLCJzdWJqZWN0IjoidGVzdGhlbGxvIn0.uqcz2KXo52I_M2zp7yd9V8dyluifrG8yPNBs-2VeUnw");
                user.setTokenExpiredAt(System.currentTimeMillis() - 100000000L);
                userRepository.save(user);

                mockMvc.perform(get(UserConstants.BASE_PATH + "/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization","Bearer " + user.getToken()))
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
