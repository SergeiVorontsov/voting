package com.vorsa.voting.web.menu;

import com.vorsa.voting.model.Menu;
import com.vorsa.voting.model.Restaurant;
import com.vorsa.voting.to.MenuTo;
import com.vorsa.voting.util.JsonUtil;
import com.vorsa.voting.util.MenuUtil;
import com.vorsa.voting.web.AbstractControllerTest;
import com.vorsa.voting.web.user.UniqueMailValidator;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminMenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + ADMIN_RESTAURANT1_ID +'/'+ "menus" + '/' + ADMIN_RESTAURANT1_MENU1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(createTo(adminMenuForRestaurant1)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + ADMIN_RESTAURANT1_ID +'/'+ "menus" + '/' + ADMIN_RESTAURANT1_MENU1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(createTo(adminMenuForRestaurant1)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() {
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() {
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() {
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDuplicate() throws Exception {
        Restaurant updated = new Restaurant(adminRestaurant1);
        updated.setName(userRestaurant1.getName());
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + ADMIN_RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(UniqueMailValidator.EXCEPTION_DUPLICATE_EMAIL)));
    }
/*

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        User expected = new User(null, "New", USER_MAIL, "newPass", Role.USER, Role.ADMIN);
        perform(MockMvcRequestBuilders.post(AdminUserController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(expected, "newPass")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(UniqueMailValidator.EXCEPTION_DUPLICATE_EMAIL)));
    }
*/
}