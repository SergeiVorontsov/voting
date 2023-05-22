package com.vorsa.voting.to;

import com.vorsa.voting.model.Dish;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuTo extends BaseTo {
    LocalDate date;
    List<Dish> dishes;

    public MenuTo(Integer id, LocalDate date, List<Dish> dishes) {
        super(id);
        this.date = date;
        this.dishes = dishes;
    }
}
