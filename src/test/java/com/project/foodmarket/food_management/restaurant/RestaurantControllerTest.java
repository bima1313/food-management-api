package com.project.foodmarket.food_management.restaurant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.project.foodmarket.food_management.constants.RestaurantConstants;
import com.project.foodmarket.food_management.document.Restaurant;
import com.project.foodmarket.food_management.document.User;
import com.project.foodmarket.food_management.model.WebResponse;
import com.project.foodmarket.food_management.model.restaurant.RestaurantRegisterRequest;
import com.project.foodmarket.food_management.model.restaurant.merchant.MerchantRestaurantResponse;
import com.project.foodmarket.food_management.repository.RestaurantRepository;
import com.project.foodmarket.food_management.repository.UserRepository;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RestaurantControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RestaurantRepository restaurantRepository;

        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        void setup() {
                userRepository.deleteAll();
                restaurantRepository.deleteAll();
        }

        @Test
        void registerUnauthorizedTokenNotSend() throws Exception {
                RestaurantRegisterRequest request = new RestaurantRegisterRequest();
                request.setName("starbucks");
                request.setAddress("M.H. Thamrin Street Number 25A, Jakarta, Indonesia");
                request.setSettings(Map.of(
                                "isDineInEnabled", true));

                mockMvc.perform(post(RestaurantConstants.REGISTER_PATH).accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpectAll(status().isUnauthorized()).andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<String>>() {
                                                        });
                                        assertNotNull(response.getError());
                                });
        }

        @Test
        void registerSuccess() throws Exception {
                User user = new User();
                user.setToken("eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiJjNmQ4YTM4My1hYWQ3LTQ4N2UtODBhZS00NWY5ZjViZTg3MzgiLCJpc3MiOiJmb29kLW1hcmtldCIsInN1YiI6ImhlbGxvd29ybGQiLCJhdWQiOlsidXNlciJdLCJleHAiOjE3Njg3NjMzMzQsIm5iZiI6MTc2ODE1ODUzNCwiaWF0IjoxNzY4MTU4NTM0fQ.KFtqJoo7X75a10BSoLRav6v817U2m86GDrfy0hsg4a148TK3IWX-57VIWoka1qb3");
                user.setTokenExpiredAt(1768763335093L);
                userRepository.save(user);

                RestaurantRegisterRequest request = new RestaurantRegisterRequest();
                request.setName("starbucks");
                request.setAddress("M.H. Thamrin Street Number 25A, Jakarta, Indonesia");
                request.setSettings(Map.of(
                                "isDineInEnabled", true));

                mockMvc.perform(post(RestaurantConstants.REGISTER_PATH)
                                .header("Authorization", "Bearer " + user.getToken())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpectAll(status().isOk()).andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<String>>() {

                                                        });
                                        assertNull(response.getError());
                                });
        }

        @Test
        void getMerchantRestaurantsNotFound() throws Exception {
                User user = new User();
                user.setId("eyJhbfaGkiJIUIkRjCJ9");
                user.setToken("eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiJjNmQ4YTM4My1hYWQ3LTQ4N2UtODBhZS00NWY5ZjViZTg3MzgiLCJpc3MiOiJmb29kLW1hcmtldCIsInN1YiI6ImhlbGxvd29ybGQiLCJhdWQiOlsidXNlciJdLCJleHAiOjE3Njg3NjMzMzQsIm5iZiI6MTc2ODE1ODUzNCwiaWF0IjoxNzY4MTU4NTM0fQ.KFtqJoo7X75a10BSoLRav6v817U2m86GDrfy0hsg4a148TK3IWX-57VIWoka1qb3");
                user.setTokenExpiredAt(1768763335093L);
                userRepository.save(user);
                mockMvc.perform(get(RestaurantConstants.MERCHANT_BASE_PATH)
                                .header("Authorization", "Bearer " + user.getToken())
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpectAll(
                                                status().isNotFound())
                                .andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<String>>() {
                                                        });
                                        assertNotNull(response.getError());
                                });
        }

        @Test
        void getMerchantRestaurantsSuccess() throws Exception {
                User user = new User();
                user.setId("eyJhbfaGkiJIUIkRjCJ9");
                user.setToken("eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiJjNmQ4YTM4My1hYWQ3LTQ4N2UtODBhZS00NWY5ZjViZTg3MzgiLCJpc3MiOiJmb29kLW1hcmtldCIsInN1YiI6ImhlbGxvd29ybGQiLCJhdWQiOlsidXNlciJdLCJleHAiOjE3Njg3NjMzMzQsIm5iZiI6MTc2ODE1ODUzNCwiaWF0IjoxNzY4MTU4NTM0fQ.KFtqJoo7X75a10BSoLRav6v817U2m86GDrfy0hsg4a148TK3IWX-57VIWoka1qb3");
                user.setTokenExpiredAt(1768763335093L);
                userRepository.save(user);

                List<Restaurant> allRestaurant = new ArrayList<Restaurant>();
                Restaurant restaurant1 = new Restaurant();
                restaurant1.setId("OjE3Njg3NjMz5ZjViZTg3MzgiLCJpc3MiOi");
                restaurant1.setName("starbucks");
                restaurant1.setOwnerId("eyJhbfaGkiJIUIkRjCJ9");
                restaurant1.setAddress("M.H. Thamrin Street Number 25A, Jakarta, Indonesia");
                restaurant1.setSettings(Map.of(
                                "isDineInEnabled", true));
                restaurant1.setRating(4.8);
                allRestaurant.add(restaurant1);

                Restaurant restaurant2 = new Restaurant();
                restaurant2.setId("5ZjViZTg3MzgiLCJpc3MiOiJmb29kLW1hcmtldCIsInN1");
                restaurant2.setOwnerId("eyJhbfaGkiJIUIkRjCJ9");
                restaurant2.setName("burger king");
                restaurant2.setAddress("Pemuda Street Number 15, Jakarta, Indonesia");
                restaurant2.setSettings(Map.of(
                                "isDineInEnabled", true));
                restaurant2.setRating(3.9);
                allRestaurant.add(restaurant2);
                restaurantRepository.saveAll(allRestaurant);

                mockMvc.perform(get(RestaurantConstants.MERCHANT_BASE_PATH)
                                .header("Authorization", "Bearer " + user.getToken())
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpectAll(
                                                status().isOk())
                                .andDo(result -> {
                                        WebResponse<MerchantRestaurantResponse> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<MerchantRestaurantResponse>>() {
                                                        });
                                        assertNull(response.getError());
                                        assertNotNull(response.getData().getRestaurants());
                                });
        }
}
