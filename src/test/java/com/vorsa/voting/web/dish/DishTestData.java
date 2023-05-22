package com.vorsa.voting.web.dish;

import com.vorsa.voting.model.Dish;
import com.vorsa.voting.web.MatcherFactory;

import static com.vorsa.voting.web.menu.MenuTestData.*;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "menu");

    public static final int ADMIN_RESTAURANT1_MENU1_DISH1_ID = 1;
    public static final int ADMIN_RESTAURANT1_MENU1_DISH2_ID = 2;

    public static final int ADMIN_RESTAURANT1_MENU2_DISH1_ID = 3;
    public static final int ADMIN_RESTAURANT1_MENU2_DISH2_ID = 4;

    public static final int ADMIN_RESTAURANT2_MENU1_DISH1_ID = 5;
    public static final int ADMIN_RESTAURANT2_MENU1_DISH2_ID = 6;
    public static final int ADMIN_RESTAURANT2_MENU1_DISH3_ID = 7;

    public static final Dish ADMIN_RESTAURANT_1_MENU_1_DISH_1 = new Dish(ADMIN_RESTAURANT1_MENU1_DISH1_ID, "Filet", 80L, null);
    public static final Dish ADMIN_RESTAURANT_1_MENU_1_DISH_2 = new Dish(ADMIN_RESTAURANT1_MENU1_DISH2_ID, "Steak", 90L, null);

    public static final Dish ADMIN_RESTAURANT_1_MENU_2_DISH_1 = new Dish(ADMIN_RESTAURANT1_MENU2_DISH1_ID, "Pizza", 50L, null);
    public static final Dish ADMIN_RESTAURANT_1_MENU_2_DISH_2 = new Dish(ADMIN_RESTAURANT1_MENU2_DISH2_ID, "Tiramisu", 30L, null);

    public static final Dish ADMIN_RESTAURANT_2_MENU_1_DISH_1 = new Dish(ADMIN_RESTAURANT2_MENU1_DISH1_ID, "Burger", 50L, null);
    public static final Dish ADMIN_RESTAURANT_2_MENU_1_DISH_2 = new Dish(ADMIN_RESTAURANT2_MENU1_DISH2_ID, "Minestrone", 30L, null);
    public static final Dish ADMIN_RESTAURANT_2_MENU_1_DISH_3 = new Dish(ADMIN_RESTAURANT2_MENU1_DISH3_ID, "Club Sandwich", 40L, null);

    static {
        ADMIN_RESTAURANT_1_MENU_1_DISH_1.setMenu(adminMenu1ForRestaurant1);
        ADMIN_RESTAURANT_1_MENU_1_DISH_1.setMenu(adminMenu1ForRestaurant1);
        ADMIN_RESTAURANT_1_MENU_2_DISH_1.setMenu(adminMenu2ForRestaurant1);
        ADMIN_RESTAURANT_1_MENU_2_DISH_2.setMenu(adminMenu2ForRestaurant1);
        ADMIN_RESTAURANT_2_MENU_1_DISH_1.setMenu(adminMenu1ForRestaurant2);
        ADMIN_RESTAURANT_2_MENU_1_DISH_2.setMenu(adminMenu1ForRestaurant2);
        ADMIN_RESTAURANT_2_MENU_1_DISH_3.setMenu(adminMenu1ForRestaurant2);
    }

    public static Dish getNew() {
        return new Dish(null, "New", 100L, null);
    }

    public static Dish getUpdated() {
        return new Dish(ADMIN_RESTAURANT1_MENU1_DISH1_ID, "New name", 80L, adminMenu1ForRestaurant1);
    }
}
