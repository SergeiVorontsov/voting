package com.vorsa.voting.web.meal;

import com.vorsa.voting.model.Meal;

import static com.vorsa.voting.web.menu.MenuTestData.adminMenuForRestaurant1;

public class MealTestData {

    public static final Meal adminRestaurant1Menu1Meal1 = new Meal(4, "Тирамису", 50L, null);
    public static final Meal adminRestaurant1Menu1Meal2 = new Meal(5, "Пана кота", 50L, null);
    public static final Meal adminRestaurant1Menu1Meal3 = new Meal(6, "Минестроне", 70L, null);
    public static final Meal adminRestaurant1Menu1Meal4 = new Meal(7, "Борщ", 70L, null);

    static {
        adminRestaurant1Menu1Meal1.setMenu(adminMenuForRestaurant1);
        adminRestaurant1Menu1Meal2.setMenu(adminMenuForRestaurant1);
        adminRestaurant1Menu1Meal3.setMenu(adminMenuForRestaurant1);
        adminRestaurant1Menu1Meal4.setMenu(adminMenuForRestaurant1);
    }

}
