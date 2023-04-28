package com.vorsa.voting.web.restaurant;

import com.vorsa.voting.model.Restaurant;
import com.vorsa.voting.to.RestaurantTo;
import com.vorsa.voting.web.MatcherFactory;
import com.vorsa.voting.web.menu.MenuTestData;

import java.util.List;

import static com.vorsa.voting.util.RestaurantUtil.getTos;
import static com.vorsa.voting.web.menu.MenuTestData.adminMenuForRestaurant1;

import static com.vorsa.voting.web.menu.MenuTestData.userMenuForRestaurant1;
import static com.vorsa.voting.web.user.UserTestData.admin;
import static com.vorsa.voting.web.user.UserTestData.user;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "user", "menus");
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingEqualsComparator(RestaurantTo.class);

    /*public static MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MENU_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
                    //     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("user", "menu.restaurant", "menu.meals").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });*/
    public static MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_WITH_MENU_MATCHER =
            MatcherFactory.usingAssertions(RestaurantTo.class,
                    //     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields( "menu.meals").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });


    //public static MatcherFactory.Matcher<MealTo> MEAL_TO_MATCHER = MatcherFactory.usingEqualsComparator(MealTo.class);

    public static final int USER_RESTAURANT1_ID = 1;
    public static final int ADMIN_RESTAURANT1_ID = 2;
    public static final int NOT_FOUND_RESTAURANT_ID = 0;

    public static final Restaurant userRestaurant1 = new Restaurant(USER_RESTAURANT1_ID,"Domino Pizza",user, userMenuForRestaurant1);
    public static final Restaurant adminRestaurant1 = new Restaurant(ADMIN_RESTAURANT1_ID,"Pizza Hut",admin, adminMenuForRestaurant1);

   /* static {
        userRestaurant1.setMenus(List.of(userMenuForRestaurant1));
        adminRestaurant1.setMenus(List.of(adminMenuForRestaurant1));
    }*/

    public static final List<RestaurantTo> tos = getTos(adminRestaurant1);

    public static final List<Restaurant> restaurants = List.of(userRestaurant1,adminRestaurant1);
    public static final List<Restaurant> restaurantsWithMenu = List.of(userRestaurant1);

    public static Restaurant getNew() {
        return new Restaurant(null,"New restaurant", user, MenuTestData.getNew());
    }

    public static Restaurant getUpdated() {
        return new Restaurant(ADMIN_RESTAURANT1_ID, "New name", user, adminMenuForRestaurant1);}
}
