package com.vorsa.voting.service;

import com.vorsa.voting.model.Dish;
import com.vorsa.voting.repository.DishRepository;
import com.vorsa.voting.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DishService {
    private final MenuRepository menuRepository;
    private final DishRepository dishRepository;

    @Transactional
    public Dish save(int menuId, Dish dish) {
        dish.setMenu(menuRepository.getExisted(menuId));
        return dishRepository.save(dish);
    }
}
