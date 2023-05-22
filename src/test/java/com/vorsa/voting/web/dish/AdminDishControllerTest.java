package com.vorsa.voting.web.dish;

import com.vorsa.voting.model.Dish;
import com.vorsa.voting.repository.DishRepository;
import com.vorsa.voting.util.JsonUtil;
import com.vorsa.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.vorsa.voting.web.dish.AdminDishController.REST_URL;
import static com.vorsa.voting.web.dish.DishTestData.*;
import static com.vorsa.voting.web.menu.MenuTestData.ADMIN_RESTAURANT1_MENU1_ID;
import static com.vorsa.voting.web.restaurant.RestaurantTestData.ADMIN_RESTAURANT1_ID;
import static com.vorsa.voting.web.user.UserTestData.ADMIN_MAIL;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminDishControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';
    private static final String REST_URL_DISH = REST_URL_SLASH + ADMIN_RESTAURANT1_ID + '/' + "menus" + '/' + ADMIN_RESTAURANT1_MENU1_ID + '/' + "dishes";
    private static final String REST_URL_DISH_SLASH = REST_URL_DISH + '/';

    @Autowired
    private DishRepository repository;

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_DISH))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_DISH_SLASH + ADMIN_RESTAURANT1_MENU1_DISH1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(ADMIN_RESTAURANT_1_MENU_1_DISH_1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_DISH))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(ADMIN_RESTAURANT_1_MENU_1_DISH_1, ADMIN_RESTAURANT_1_MENU_1_DISH_2));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_DISH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)));

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(repository.getExisted(newId), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_DISH_SLASH + ADMIN_RESTAURANT1_MENU1_DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(repository.getExisted(ADMIN_RESTAURANT1_MENU1_DISH1_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_DISH_SLASH + ADMIN_RESTAURANT1_MENU1_DISH1_ID))
                .andExpect(status().isNoContent());
        assertNull(repository.get(ADMIN_RESTAURANT1_MENU1_DISH1_ID));
    }
}