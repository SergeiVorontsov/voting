package com.vorsa.voting.to;

import com.vorsa.voting.model.Menu;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {
    Menu menu;

    public RestaurantTo(Integer id, String name, Menu menu) {
        super(id, name);
        this.menu = menu;
    }
}