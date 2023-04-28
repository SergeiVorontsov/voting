package com.vorsa.voting.util;

import com.vorsa.voting.model.Menu;
import com.vorsa.voting.model.Restaurant;
import com.vorsa.voting.to.MenuTo;
import com.vorsa.voting.to.RestaurantTo;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MenuUtil {
/*    public static List<MenuTo> getTos(Collection<Menu> menus) {
        return menus.stream()
                .map(MenuUtil::createTo)
                .collect(Collectors.toList());
    }

    public static List<RestaurantTo> getTos(Restaurant... restaurants) {
        return Arrays.stream(restaurants)
                .map(RestaurantUtil::createTo)
                .collect(Collectors.toList());
    }*/

    public static MenuTo createTo(Menu menu) {
        return new MenuTo(menu.getId(), menu.getDate(), menu.getMeals());
    }
}
