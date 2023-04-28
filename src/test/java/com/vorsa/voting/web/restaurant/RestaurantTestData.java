package com.vorsa.voting.web.restaurant;

import com.vorsa.voting.model.Restaurant;
import com.vorsa.voting.to.RestaurantTo;
import com.vorsa.voting.web.MatcherFactory;
import com.vorsa.voting.web.menu.MenuTestData;

import java.util.Collections;

import static com.vorsa.voting.web.menu.MenuTestData.*;
import static com.vorsa.voting.web.user.UserTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "user", "menus");
    public static MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_WITH_MENU_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class, "menus.meals");

    public static final int ADMIN_RESTAURANT1_ID = 1;
    public static final int ADMIN_RESTAURANT2_ID = 2;
    public static final int ANOTHER_ADMIN_RESTAURANT_ID = 3;
    public static final int NOT_FOUND_RESTAURANT_ID = 0;

    public static final Restaurant adminRestaurant1 = new Restaurant(ADMIN_RESTAURANT1_ID, "Domino Pizza", null, Collections.emptyList());
    public static final Restaurant adminRestaurant2 = new Restaurant(ADMIN_RESTAURANT2_ID, "Pizza Hut", null, Collections.emptyList());
    public static final Restaurant anotherAdminRestaurant = new Restaurant(ANOTHER_ADMIN_RESTAURANT_ID, "Alfa", null, Collections.emptyList());

    public static final Restaurant adminRestaurant1WithActualMenu = new Restaurant(ADMIN_RESTAURANT1_ID, "Domino Pizza", null, Collections.emptyList());

    static {
        adminRestaurant1.setUser(admin);
        adminRestaurant2.setUser(admin);
        adminRestaurant1WithActualMenu.setUser(admin);
        adminRestaurant1.setMenus(adminMenu1ForRestaurant1, adminMenu2ForRestaurant1);
        adminRestaurant2.setMenus(adminMenu1ForRestaurant2);
        anotherAdminRestaurant.setUser(anotherAdmin);
        anotherAdminRestaurant.setMenus(anotherAdminMenu);
        adminRestaurant1WithActualMenu.setMenus(adminMenu1ForRestaurant1);
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "New restaurant", user, MenuTestData.getNew());
    }

    public static Restaurant getUpdated() {
        return new Restaurant(ADMIN_RESTAURANT1_ID, "New name", user, adminMenu1ForRestaurant1);
    }
}
