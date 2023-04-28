package com.vorsa.voting.web.restaurant;

import com.vorsa.voting.repository.RestaurantRepository;
import com.vorsa.voting.repository.UserRepository;
import com.vorsa.voting.to.RestaurantTo;
import com.vorsa.voting.web.AbstractControllerTest;
import com.vorsa.voting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.vorsa.voting.util.RestaurantUtil.getTos;
import static com.vorsa.voting.web.restaurant.RestaurantController.REST_URL;
import static com.vorsa.voting.web.restaurant.RestaurantTestData.*;
import static com.vorsa.voting.web.user.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class RestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + USER_RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(userRestaurant1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(userRestaurant1,adminRestaurant1));
    }

/*    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllWithMenu() throws Exception {
       List<RestaurantTo> t = tos;
        perform(MockMvcRequestBuilders.get(REST_URL + "/with-today-menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_WITH_MENU_MATCHER.contentJson(tos));
    }*/

/*    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + USER_RESTAURANT1_ID + "/with-today-menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_WITH_MENU_MATCHER.contentJson(getTos(userRestaurant1)));
    }*/

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + USER_RESTAURANT1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + NOT_FOUND_RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}