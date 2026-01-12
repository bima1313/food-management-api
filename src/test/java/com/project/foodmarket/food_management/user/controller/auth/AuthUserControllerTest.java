package com.project.foodmarket.food_management.user.controller.auth;

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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import com.project.foodmarket.food_management.constants.UserConstants;
import com.project.foodmarket.food_management.document.User;
import com.project.foodmarket.food_management.model.TokenResponse;
import com.project.foodmarket.food_management.model.WebResponse;
import com.project.foodmarket.food_management.model.user.UserLoginRequest;
import com.project.foodmarket.food_management.model.user.UserResponse;
import com.project.foodmarket.food_management.model.user.UserUpdateRequest;
import com.project.foodmarket.food_management.repository.UserRepository;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthUserControllerTest {

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
        void loginFailedUserNotFoundTest() throws Exception {
                UserLoginRequest userLoginRequest = new UserLoginRequest();
                userLoginRequest.setEmail("test@gmail.com");
                userLoginRequest.setPassword("secretPassword");
                mockMvc.perform(post(UserConstants.LOGIN_PATH)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userLoginRequest)))
                                .andExpectAll(
                                                status().isUnauthorized())
                                .andDo(result -> {
                                        WebResponse<UserResponse> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<UserResponse>>() {
                                                        });

                                        assertNotNull(response.getError());
                                });
        }

        @Test
        void loginFailedWrongPassword() throws Exception {
                User user = new User();
                user.setEmail("test@gmail.com");
                user.setPassword(BCrypt.hashpw("hello_world2026", BCrypt.gensalt()));
                user.setUsername("helloworld2026");
                user.setName("world");
                userRepository.save(user);

                UserLoginRequest userLoginRequest = new UserLoginRequest();
                userLoginRequest.setEmail("test@gmail.com");
                userLoginRequest.setPassword("secretPassword");
                mockMvc.perform(post(UserConstants.LOGIN_PATH)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userLoginRequest)))
                                .andExpectAll(
                                                status().isUnauthorized())
                                .andDo(result -> {
                                        WebResponse<UserResponse> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<UserResponse>>() {
                                                        });

                                        assertNotNull(response.getError());
                                });
        }

        @Test
        void loginSuccess() throws Exception {
                User user = new User();
                user.setId(UUID.randomUUID().toString());
                user.setEmail("test@gmail.com");
                user.setUsername("helloworld2026");
                user.setPassword(BCrypt.hashpw("secretPassword", BCrypt.gensalt()));
                user.setName("world");
                userRepository.save(user);

                UserLoginRequest userLoginRequest = new UserLoginRequest();
                userLoginRequest.setEmail("test@gmail.com");
                userLoginRequest.setPassword("secretPassword");

                mockMvc.perform(post(UserConstants.LOGIN_PATH)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userLoginRequest)))
                                .andExpectAll(
                                                status().isOk())
                                .andDo(result -> {
                                        WebResponse<TokenResponse> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<TokenResponse>>() {
                                                        });

                                        assertNull(response.getError());
                                        assertNotNull(response.getData().getToken());
                                        assertNotNull(response.getData().getTokenExpiredAt());

                                        User userDb = userRepository.findByEmail("test@gmail.com")
                                                        .orElse(null);

                                        assertNotNull(userDb);
                                        assertEquals(userDb.getToken(), response.getData().getToken());
                                });
        }

        @Test
        void logoutFailed() throws Exception {
                mockMvc.perform(delete(UserConstants.LOGOUT_PATH)
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
                User user = new User();
                user.setId("test26138f");
                user.setEmail("test@gmail.com");
                user.setUsername("testhello");
                user.setPassword(BCrypt.hashpw("helloworld", BCrypt.gensalt()));
                user.setName("test2");
                user.setToken(
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjIxMTRzZmFhIiwiaXNzdWVyIjoiZm9vZC1tYXJrZXQiLCJzdWJqZWN0IjoidGVzdGhlbGxvIn0.uqcz2KXo52I_M2zp7yd9V8dyluifrG8yPNBs-2VeUnw");
                user.setTokenExpiredAt(System.currentTimeMillis() + 100000000L);
                userRepository.save(user);

                mockMvc.perform(
                                delete(UserConstants.LOGOUT_PATH)
                                                .header("Authorization", "Bearer " + user.getToken())
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

                                        User userDb = userRepository.findByEmail("test@gmail.com")
                                                        .orElse(null);

                                        assertNotNull(userDb);
                                        assertNull(userDb.getToken());
                                        assertEquals(0, userDb.getTokenExpiredAt());
                                });
        }

        @Test
        void updateUserUnauthorized() throws Exception {
                String userId = "test26138f";
                User user = new User();
                user.setId(userId);
                user.setEmail("test@gmail.com");
                user.setUsername("testhello");
                user.setPassword(BCrypt.hashpw("helloworld", BCrypt.gensalt()));
                user.setName("test2");
                user.setToken(
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjIxMTRzZmFhIiwiaXNzdWVyIjoiZm9vZC1tYXJrZXQiLCJzdWJqZWN0IjoidGVzdGhlbGxvIn0.uqcz2KXo52I_M2zp7yd9V8dyluifrG8yPNBs-2VeUnw");
                user.setTokenExpiredAt(System.currentTimeMillis() + 100000000L);
                userRepository.save(user);

                UserUpdateRequest request = new UserUpdateRequest();

                mockMvc.perform(patch(UserConstants.UPDATE_PATH, userId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpectAll(status().isUnauthorized())
                                .andDo(result -> {
                                        WebResponse<UserResponse> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<UserResponse>>() {
                                                        });

                                        assertNotNull(response.getError());
                                });
        }

        @Test
        void updateUserFailedWrongEmailFormat() throws Exception {
                String userId = "test26138f";
                User user = new User();
                user.setId(userId);
                user.setEmail("test@gmail.com");
                user.setUsername("testhello");
                user.setPassword(BCrypt.hashpw("helloworld", BCrypt.gensalt()));
                user.setName("test2");
                user.setToken(
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjIxMTRzZmFhIiwiaXNzdWVyIjoiZm9vZC1tYXJrZXQiLCJzdWJqZWN0IjoidGVzdGhlbGxvIn0.uqcz2KXo52I_M2zp7yd9V8dyluifrG8yPNBs-2VeUnw");
                user.setTokenExpiredAt(System.currentTimeMillis() + 100000000L);
                userRepository.save(user);

                UserUpdateRequest request = new UserUpdateRequest();
                request.setEmail("hello@gmailcom");

                mockMvc.perform(patch(UserConstants.UPDATE_PATH, userId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .header("Authorization", "Bearer " + user.getToken()))
                                .andExpectAll(status().isBadRequest())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<String>>() {
                                                        });

                                        assertNotNull(response.getError());
                                });
        }

        @Test
        void updateUserSuccess() throws Exception {
                String userId = "test26138f";
                User user = new User();
                user.setId(userId);
                user.setEmail("test@gmail.com");
                user.setUsername("testhello");
                user.setPassword(BCrypt.hashpw("helloworld", BCrypt.gensalt()));
                user.setName("test2");
                user.setToken(
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjIxMTRzZmFhIiwiaXNzdWVyIjoiZm9vZC1tYXJrZXQiLCJzdWJqZWN0IjoidGVzdGhlbGxvIn0.uqcz2KXo52I_M2zp7yd9V8dyluifrG8yPNBs-2VeUnw");
                user.setTokenExpiredAt(System.currentTimeMillis() + 100000000L);
                userRepository.save(user);

                UserUpdateRequest request = new UserUpdateRequest();
                request.setEmail("hello@gmail.com");
                request.setName("world");
                request.setPassword("hello_world_2026");

                mockMvc.perform(patch(UserConstants.UPDATE_PATH, userId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .header("Authorization", "Bearer " + user.getToken()))
                                .andExpectAll(status().isOk())
                                .andDo(result -> {
                                        WebResponse<UserResponse> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<UserResponse>>() {
                                                        });

                                        assertNull(response.getError());
                                        assertEquals("world", response.getData().getName());
                                        assertEquals("hello@gmail.com", response.getData().getEmail());
                                        User userDb = userRepository.findByEmail("hello@gmail.com")
                                                        .orElse(null);

                                        assertNotNull(userDb);
                                        assertTrue(BCrypt.checkpw("hello_world_2026", userDb.getPassword()));
                                });
        }

        @Test
        void deleteAccountFailed() throws Exception {
                String userId = "test26138f";
                User user = new User();
                user.setId(userId);
                user.setEmail("test@gmail.com");
                user.setUsername("testhello");
                user.setPassword(BCrypt.hashpw("helloworld", BCrypt.gensalt()));
                user.setName("test2");
                user.setToken(
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjIxMTRzZmFhIiwiaXNzdWVyIjoiZm9vZC1tYXJrZXQiLCJzdWJqZWN0IjoidGVzdGhlbGxvIn0.uqcz2KXo52I_M2zp7yd9V8dyluifrG8yPNBs-2VeUnw");
                user.setTokenExpiredAt(System.currentTimeMillis() + 100000000L);
                userRepository.save(user);

                mockMvc.perform(delete(UserConstants.DELETE_ACCOUNT_PATH, userId)
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpectAll(
                                                status().isUnauthorized())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<String>>() {
                                                        });
                                        assertNotNull(response.getError());
                                });
        }

        @Test
        void deleteAccountSuccess() throws Exception {
                String userId = "test26138f";
                User user = new User();
                user.setId(userId);
                user.setEmail("test@gmail.com");
                user.setUsername("testhello");
                user.setPassword(BCrypt.hashpw("helloworld", BCrypt.gensalt()));
                user.setName("test2");
                user.setToken(
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjIxMTRzZmFhIiwiaXNzdWVyIjoiZm9vZC1tYXJrZXQiLCJzdWJqZWN0IjoidGVzdGhlbGxvIn0.uqcz2KXo52I_M2zp7yd9V8dyluifrG8yPNBs-2VeUnw");
                user.setTokenExpiredAt(System.currentTimeMillis() + 100000000L);
                userRepository.save(user);

                mockMvc.perform(
                                delete(UserConstants.DELETE_ACCOUNT_PATH, userId)
                                                .header("Authorization", "Bearer " + user.getToken())
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

                                        User userDb = userRepository.findByEmail("test@gmail.com")
                                                        .orElse(null);

                                        assertNull(userDb);
                                });
        }
}
