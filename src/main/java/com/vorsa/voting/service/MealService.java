package com.vorsa.voting.service;

import com.vorsa.voting.model.Meal;
import com.vorsa.voting.repository.MealRepository;
import com.vorsa.voting.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MealService {
    private final MenuRepository menuRepository;
    private final MealRepository mealRepository;

    @Transactional
    public Meal save(int menuId, Meal meal) {
        meal.setMenu(menuRepository.getExisted(menuId));
        return mealRepository.save(meal);
    }
}
