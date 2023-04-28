package com.vorsa.voting.web.menu;

import com.vorsa.voting.model.Menu;
import com.vorsa.voting.to.MenuTo;
import com.vorsa.voting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static com.vorsa.voting.util.MenuUtil.createTo;
import static com.vorsa.voting.web.meal.MealTestData.*;
import static com.vorsa.voting.web.restaurant.RestaurantTestData.adminRestaurant1;
import static com.vorsa.voting.web.restaurant.RestaurantTestData.userRestaurant1;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant", "meals");
    public static final MatcherFactory.Matcher<MenuTo> MENU_TO_MATCHER = MatcherFactory.usingEqualsComparator(MenuTo.class);


    public static final int USER_RESTAURANT1_MENU1_ID = 1;
    public static final int ADMIN_RESTAURANT1_MENU1_ID = 2;
    public static final int NOT_FOUND_RESTAURANT_MENU_ID = 0;


    public static final Menu userMenuForRestaurant1 = new Menu(1, null, LocalDate.now());
    public static final Menu adminMenuForRestaurant1 = new Menu(2, null, LocalDate.of(2023, 2, 10));

    static {
        adminMenuForRestaurant1.setRestaurant(userRestaurant1);
        adminMenuForRestaurant1.setMeals(List.of(adminRestaurant1Menu1Meal1, adminRestaurant1Menu1Meal2, adminRestaurant1Menu1Meal3, adminRestaurant1Menu1Meal4));
    }

    public static final MenuTo menuTo = createTo(adminMenuForRestaurant1);

    public static Menu getNew() {
        return new Menu(null, adminRestaurant1, LocalDate.now().plusDays(1));
    }
}
