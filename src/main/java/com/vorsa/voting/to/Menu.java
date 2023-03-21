package com.vorsa.voting.to;

import com.vorsa.voting.model.Meal;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Menu {
    private int restaurantId;
    private List<Meal> meals;
}
