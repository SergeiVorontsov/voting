package com.vorsa.voting.web.menu;

import com.vorsa.voting.model.Menu;
import com.vorsa.voting.repository.MenuRepository;
import com.vorsa.voting.util.JsonUtil;
import com.vorsa.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.vorsa.voting.util.MenuUtil.createTo;
import static com.vorsa.voting.web.menu.AdminMenuController.REST_URL;
import static com.vorsa.voting.web.menu.MenuTestData.*;
import static com.vorsa.voting.web.restaurant.RestaurantTestData.*;
import static com.vorsa.voting.web.user.UserTestData.ADMIN_MAIL;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminMenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';
    private static final String REST_URL_MENU = REST_URL_SLASH + ADMIN_RESTAURANT1_ID + '/' + "menus";
    private static final String REST_URL_MENU_SLASH = REST_URL_MENU + '/';

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MENU))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MENU_SLASH + ADMIN_RESTAURANT1_MENU1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(createTo(adminMenu1ForRestaurant1)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MENU_SLASH + "by-date?date=" + adminMenu1ForRestaurant1.getDate()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(createTo(adminMenu1ForRestaurant1)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MENU))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(adminMenu1ForRestaurant1, adminMenu2ForRestaurant1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Menu newMenu = MenuTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(AdminMenuControllerTest.REST_URL_MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)));

        Menu created = MENU_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(menuRepository.getExisted(newId), newMenu);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_MENU_SLASH + ADMIN_RESTAURANT1_MENU1_ID))
                .andExpect(status().isNoContent());
        assertNull(menuRepository.get(ADMIN_RESTAURANT1_MENU1_ID));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteDataConflict() throws Exception {
        perform(MockMvcRequestBuilders
                .delete(REST_URL_SLASH + ANOTHER_ADMIN_RESTAURANT_ID + '/' + "menus" + '/' + ANOTHER_ADMIN_RESTAURANT_MENU_ID))
                .andExpect(status().isConflict());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Menu expected = new Menu(null, adminRestaurant1, LocalDate.now());
        perform(MockMvcRequestBuilders.post(REST_URL_MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(UniqueDateValidator.EXCEPTION_DUPLICATE_DATE)));
    }
}