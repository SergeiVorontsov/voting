package com.vorsa.voting.to;

import com.vorsa.voting.model.Meal;
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
    List<Meal> meals;

    public MenuTo(Integer id, LocalDate date, List<Meal> meals) {
        super(id);
        this.date = date;
        this.meals = meals;
    }
}
