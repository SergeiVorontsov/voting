package com.vorsa.voting.service;

import com.vorsa.voting.model.Menu;
import com.vorsa.voting.model.Restaurant;
import com.vorsa.voting.repository.MealRepository;
import com.vorsa.voting.repository.RestaurantRepository;
import com.vorsa.voting.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    @Transactional
    public Restaurant save(int userId, Restaurant restaurant) {
        restaurant.setUser(userRepository.getExisted(userId));
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public Restaurant getWithMenuAndDishes(int restaurantId) {
        Restaurant restaurant = restaurantRepository.getExistedWithMenu(restaurantId);

        return setMenuWithDishes(restaurant);
    }

    @Transactional
    public List<Restaurant> getAllWithMenuAndDishes() {
        List<Restaurant> restaurants = restaurantRepository.getAllWithMenu(LocalDate.now());
        restaurants.forEach(this::setMenuWithDishes);
        return restaurants;
    }

    private Restaurant setMenuWithDishes(Restaurant restaurant) {
        Menu menu = restaurant.getMenus().get(0);
        menu.setMeals(mealRepository.getAll(menu.id()));
        return restaurant;
    }
}