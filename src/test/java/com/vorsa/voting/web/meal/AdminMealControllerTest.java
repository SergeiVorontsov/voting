package com.vorsa.voting.web.meal;

import com.vorsa.voting.model.Meal;
import com.vorsa.voting.repository.MealRepository;
import com.vorsa.voting.util.JsonUtil;
import com.vorsa.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.vorsa.voting.web.meal.AdminMealController.REST_URL;
import static com.vorsa.voting.web.meal.MealTestData.*;
import static com.vorsa.voting.web.menu.MenuTestData.ADMIN_RESTAURANT1_MENU1_ID;
import static com.vorsa.voting.web.restaurant.RestaurantTestData.ADMIN_RESTAURANT1_ID;
import static com.vorsa.voting.web.user.UserTestData.ADMIN_MAIL;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminMealControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';
    private static final String REST_URL_MEAL = REST_URL_SLASH + ADMIN_RESTAURANT1_ID + '/' + "menus" + '/' + ADMIN_RESTAURANT1_MENU1_ID + '/' + "meals";
    private static final String REST_URL_MEAL_SLASH = REST_URL_MEAL + '/';

    @Autowired
    private MealRepository repository;

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MEAL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MEAL_SLASH + ADMIN_RESTAURANT1_MENU1_MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(adminRestaurant1Menu1Meal1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MEAL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(adminRestaurant1Menu1Meal1, adminRestaurant1Menu1Meal2));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Meal newMeal = MealTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_MEAL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)));

        Meal created = MEAL_MATCHER.readFromJson(action);
        int newId = created.id();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(repository.getExisted(newId), newMeal);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Meal updated = MealTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_MEAL_SLASH + ADMIN_RESTAURANT1_MENU1_MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MEAL_MATCHER.assertMatch(repository.getExisted(ADMIN_RESTAURANT1_MENU1_MEAL1_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_MEAL_SLASH + ADMIN_RESTAURANT1_MENU1_MEAL1_ID))
                .andExpect(status().isNoContent());
        assertNull(repository.get(ADMIN_RESTAURANT1_MENU1_MEAL1_ID));
    }
}