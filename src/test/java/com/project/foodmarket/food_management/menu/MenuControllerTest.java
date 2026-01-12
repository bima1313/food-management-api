package com.project.foodmarket.food_management.menu;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.project.foodmarket.food_management.constants.RestaurantConstants;
import com.project.foodmarket.food_management.document.Restaurant;
import com.project.foodmarket.food_management.document.User;
import com.project.foodmarket.food_management.model.WebResponse;
import com.project.foodmarket.food_management.model.restaurant.merchant.menu.AddMenuRequest;
import com.project.foodmarket.food_management.repository.RestaurantRepository;
import com.project.foodmarket.food_management.repository.UserRepository;
import com.project.foodmarket.food_management.service.CloudinaryService;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class MenuControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private RestaurantRepository restaurantRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private CloudinaryService cloudinaryService;

        @BeforeEach
        void setup() {
                restaurantRepository.deleteAll();
                userRepository.deleteAll();
        }

        @Test
        void addMenuUnauthorizedTokenNotSend() throws Exception {
                String restaurantId = "LCJpc3MiOiJmb29kLW1hcmtl";
                AddMenuRequest request = new AddMenuRequest();
                request.setName("fish & chips");
                request.setPrice(15);
                request.setCategory("food");

                MockMultipartFile mockImageFile = new MockMultipartFile(
                                "imagefile",
                                "testing.png",
                                "image/png",
                                "image in test".getBytes());

                MockMultipartFile jsonData = new MockMultipartFile(
                                "data",
                                null,
                                "application/json",
                                objectMapper.writeValueAsBytes(request));

                mockMvc.perform(multipart(RestaurantConstants.MERCHANT_BASE_PATH + "/{restaurantId}",
                                restaurantId + "/menus")
                                .file(mockImageFile).file(jsonData).contentType(MediaType.MULTIPART_FORM_DATA))
                                .andExpectAll(status().isUnauthorized()).andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<String>>() {
                                                        });
                                        assertNotNull(response.getError());
                                });
        }

        @Test
        void addMenuRestaurantNotFound() throws Exception {
                User user = new User();
                user.setId("eyJhbfaGkiJIUIkRjCJ9");
                user.setToken(
                                "eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiJjNmQ4YTM4My1hYWQ3LTQ4N2UtODBhZS00NWY5ZjViZTg3MzgiLCJpc3MiOiJmb29kLW1hcmtldCIsInN1YiI6ImhlbGxvd29ybGQiLCJhdWQiOlsidXNlciJdLCJleHAiOjE3Njg3NjMzMzQsIm5iZiI6MTc2ODE1ODUzNCwiaWF0IjoxNzY4MTU4NTM0fQ.KFtqJoo7X75a10BSoLRav6v817U2m86GDrfy0hsg4a148TK3IWX-57VIWoka1qb3");
                user.setTokenExpiredAt(1768763335093L);
                userRepository.save(user);

                String restaurantId = "LCJpc3MiOiJmb29kLW1hcmtl";
                AddMenuRequest request = new AddMenuRequest();
                request.setName("fish & chips");
                request.setPrice(15);
                request.setCategory("food");

                MockMultipartFile mockImageFile = new MockMultipartFile(
                                "imagefile",
                                "testing.png",
                                "image/png",
                                "image in test".getBytes());

                MockMultipartFile jsonData = new MockMultipartFile(
                                "data",
                                null,
                                "application/json",
                                objectMapper.writeValueAsBytes(request));

                mockMvc.perform(multipart(RestaurantConstants.MERCHANT_BASE_PATH + "/{restaurantId}",
                                restaurantId + "/menus")
                                .header("Authorization", "Bearer " + user.getToken())
                                .file(mockImageFile).file(jsonData).contentType(MediaType.MULTIPART_FORM_DATA))
                                .andExpectAll(status().isNotFound()).andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(
                                                        result.getResponse().getContentAsString(),
                                                        new TypeReference<WebResponse<String>>() {
                                                        });
                                        assertNotNull(response.getError());
                                });
        }

        @Test
        void addMenuSuccess() throws Exception {

                Mockito.when(cloudinaryService.uploadImageFile(any(), any()))
                                .thenReturn("https://cloudinary.com/dummy-url/image75g.png");

                User user = new User();
                user.setId("eyJhbfaGkiJIUIkRjCJ9");
                user.setToken(
                                "eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiJjNmQ4YTM4My1hYWQ3LTQ4N2UtODBhZS00NWY5ZjViZTg3MzgiLCJpc3MiOiJmb29kLW1hcmtldCIsInN1YiI6ImhlbGxvd29ybGQiLCJhdWQiOlsidXNlciJdLCJleHAiOjE3Njg3NjMzMzQsIm5iZiI6MTc2ODE1ODUzNCwiaWF0IjoxNzY4MTU4NTM0fQ.KFtqJoo7X75a10BSoLRav6v817U2m86GDrfy0hsg4a148TK3IWX-57VIWoka1qb3");
                user.setTokenExpiredAt(1768763335093L);
                userRepository.save(user);

                Restaurant restaurant = new Restaurant();
                restaurant.setId("OjE3Njg3NjMz5ZjViZTg3MzgiLCJpc3MiOi");
                restaurant.setName("starbucks");
                restaurant.setOwnerId("eyJhbfaGkiJIUIkRjCJ9");
                restaurant.setAddress("M.H. Thamrin Street Number 25A, Jakarta, Indonesia");
                restaurant.setSettings(Map.of(
                                "isDineInEnabled", true));

                restaurantRepository.save(restaurant);
                AddMenuRequest request = new AddMenuRequest();
                request.setName("fish & chips");
                request.setPrice(15);
                request.setCategory("food");

                MockMultipartFile mockImageFile = new MockMultipartFile(
                                "imagefile",
                                "testing.png",
                                "image/png",
                                "image in test".getBytes());

                MockMultipartFile jsonData = new MockMultipartFile(
                                "data",
                                null,
                                "application/json",
                                objectMapper.writeValueAsBytes(request));

                mockMvc.perform(
                                multipart(RestaurantConstants.MERCHANT_BASE_PATH + "/{restaurantId}",
                                                restaurant.getId() + "/menus")
                                                .header("Authorization", "Bearer " + user.getToken())
                                                .file(mockImageFile).file(jsonData).contentType(MediaType.MULTIPART_FORM_DATA))
                                .andExpectAll(status().isOk()).andDo(result -> {
                                        WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {});

                                        assertNull(response.getError());
                                        Restaurant restaurantDb = restaurantRepository.findById(restaurant.getId()).orElse(null);

                                        assertNotNull(restaurantDb.getMenus());
                                });
        }
}