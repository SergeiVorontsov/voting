package com.vorsa.voting.to;

import com.vorsa.voting.model.Meal;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {
    List<Meal> menu;

    public RestaurantTo(Integer id, String name, List<Meal> menu) {
        super(id, name);
        this.menu = menu;
    }
}