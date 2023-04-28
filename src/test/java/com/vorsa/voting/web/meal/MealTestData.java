package com.vorsa.voting.web.meal;

import com.vorsa.voting.model.Meal;
import com.vorsa.voting.web.MatcherFactory;

import static com.vorsa.voting.web.menu.MenuTestData.*;

public class MealTestData {
    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Meal.class, "menu");

    public static final int ADMIN_RESTAURANT1_MENU1_MEAL1_ID = 1;
    public static final int ADMIN_RESTAURANT1_MENU1_MEAL2_ID = 2;

    public static final int ADMIN_RESTAURANT1_MENU2_MEAL1_ID = 3;
    public static final int ADMIN_RESTAURANT1_MENU2_MEAL2_ID = 4;

    public static final int ADMIN_RESTAURANT2_MENU1_MEAL1_ID = 5;
    public static final int ADMIN_RESTAURANT2_MENU1_MEAL2_ID = 6;
    public static final int ADMIN_RESTAURANT2_MENU1_MEAL3_ID = 7;

    public static final Meal adminRestaurant1Menu1Meal1 = new Meal(ADMIN_RESTAURANT1_MENU1_MEAL1_ID, "Filet", 80L, null);
    public static final Meal adminRestaurant1Menu1Meal2 = new Meal(ADMIN_RESTAURANT1_MENU1_MEAL2_ID, "Steak", 90L, null);

    public static final Meal adminRestaurant1Menu2Meal1 = new Meal(ADMIN_RESTAURANT1_MENU2_MEAL1_ID, "Pizza", 50L, null);
    public static final Meal adminRestaurant1Menu2Meal2 = new Meal(ADMIN_RESTAURANT1_MENU2_MEAL2_ID, "Tiramisu", 30L, null);

    public static final Meal adminRestaurant2Menu1Meal1 = new Meal(ADMIN_RESTAURANT2_MENU1_MEAL1_ID, "Burger", 50L, null);
    public static final Meal adminRestaurant2Menu1Meal2 = new Meal(ADMIN_RESTAURANT2_MENU1_MEAL2_ID, "Minestrone", 30L, null);
    public static final Meal adminRestaurant2Menu1Meal3 = new Meal(ADMIN_RESTAURANT2_MENU1_MEAL3_ID, "Club Sandwich", 40L, null);

    static {
        adminRestaurant1Menu1Meal1.setMenu(adminMenu1ForRestaurant1);
        adminRestaurant1Menu1Meal1.setMenu(adminMenu1ForRestaurant1);
        adminRestaurant1Menu2Meal1.setMenu(adminMenu2ForRestaurant1);
        adminRestaurant1Menu2Meal2.setMenu(adminMenu2ForRestaurant1);
        adminRestaurant2Menu1Meal1.setMenu(adminMenu1ForRestaurant2);
        adminRestaurant2Menu1Meal2.setMenu(adminMenu1ForRestaurant2);
        adminRestaurant2Menu1Meal3.setMenu(adminMenu1ForRestaurant2);
    }

    public static Meal getNew() {
        return new Meal(null, "New", 100L, null);
    }

    public static Meal getUpdated() {
        return new Meal(ADMIN_RESTAURANT1_MENU1_MEAL1_ID, "New name", 80L, adminMenu1ForRestaurant1);
    }
}
