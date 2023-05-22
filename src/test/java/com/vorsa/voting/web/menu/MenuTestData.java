package com.vorsa.voting.web.menu;

import com.vorsa.voting.model.Menu;
import com.vorsa.voting.to.MenuTo;
import com.vorsa.voting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.vorsa.voting.web.meal.MealTestData.*;
import static com.vorsa.voting.web.restaurant.RestaurantTestData.*;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant", "meals");
    public static final MatcherFactory.Matcher<MenuTo> MENU_TO_MATCHER = MatcherFactory.usingEqualsComparator(MenuTo.class);

    public static final int ADMIN_RESTAURANT1_MENU1_ID = 1;
    public static final int ADMIN_RESTAURANT1_MENU2_ID = 2;
    public static final int ADMIN_RESTAURANT2_MENU1_ID = 3;
    public static final int ANOTHER_ADMIN_RESTAURANT_MENU_ID = 4;

    public static final Menu adminMenu1ForRestaurant1 = new Menu(ADMIN_RESTAURANT1_MENU1_ID, null, LocalDate.now());
    public static final Menu adminMenu2ForRestaurant1 = new Menu(ADMIN_RESTAURANT1_MENU2_ID, null, LocalDate.of(2023, 2, 10));
    public static final Menu adminMenu1ForRestaurant2 = new Menu(ADMIN_RESTAURANT2_MENU1_ID, null, LocalDate.now());
    public static final Menu anotherAdminMenu = new Menu(ANOTHER_ADMIN_RESTAURANT_MENU_ID, null, LocalDate.of(2023, 2, 11));
    public static final Menu adminMenu1ForRestaurant1WithDishes = new Menu(ADMIN_RESTAURANT1_MENU1_ID, null, LocalDate.now());
    public static final Menu adminMenu1ForRestaurant2WithDishes = new Menu(ADMIN_RESTAURANT2_MENU1_ID, null, LocalDate.now());

    static {
        adminMenu1ForRestaurant1.setRestaurant(adminRestaurant1);
        adminMenu1ForRestaurant1WithDishes.setMeals(List.of(adminRestaurant1Menu1Meal1, adminRestaurant1Menu1Meal2));
        adminMenu2ForRestaurant1.setRestaurant(adminRestaurant1);
        adminMenu2ForRestaurant1.setMeals(List.of(adminRestaurant1Menu2Meal1, adminRestaurant1Menu2Meal2));
        adminMenu1ForRestaurant2.setRestaurant(adminRestaurant2);
        adminMenu1ForRestaurant2WithDishes.setMeals(List.of(adminRestaurant2Menu1Meal1, adminRestaurant2Menu1Meal2, adminRestaurant2Menu1Meal3));
        anotherAdminMenu.setRestaurant(anotherAdminRestaurant);
        anotherAdminMenu.setMeals(Collections.emptyList());
    }

    public static Menu getNew() {
        return new Menu(null, null, LocalDate.now().plusDays(1));
    }
}
