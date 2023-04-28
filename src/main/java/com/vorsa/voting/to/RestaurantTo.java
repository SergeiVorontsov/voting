package com.vorsa.voting.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {
    List<MenuTo> menus;

    public RestaurantTo(Integer id, String name, List<MenuTo> menus) {
        super(id, name);
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                " name=" + name +
                " menus=" + menus +
                '}';
    }
}