package com.vorsa.voting.util;

import com.vorsa.voting.model.Menu;
import com.vorsa.voting.to.MenuTo;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MenuUtil {
    public static List<MenuTo> getTos(Collection<Menu> menus) {
        return menus.stream()
                .map(MenuUtil::createTo)
                .collect(Collectors.toList());
    }

    public static List<MenuTo> getTos(Menu... menus) {
        return Arrays.stream(menus)
                .map(MenuUtil::createTo)
                .collect(Collectors.toList());
    }

    public static MenuTo createTo(Menu menu) {
        return new MenuTo(menu.getId(), menu.getDate(), menu.getMeals());
    }
}
