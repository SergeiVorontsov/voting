package com.vorsa.voting.controller;

import com.vorsa.voting.model.Meal;
import com.vorsa.voting.model.Restaurant;
import com.vorsa.voting.repository.MealRepository;
import com.vorsa.voting.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/restaurants")
public class RestaurantController extends AbstractController {

    @Autowired
    private RestaurantRepository RestaurantRepository;

    @Autowired
    private MealRepository mealRepository;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return RestaurantRepository.findAll();
    }

    @GetMapping(value = "{id}")
    public Optional<Restaurant> get(@PathVariable int id) {
        log.info("get user by id: {}", id);
        return RestaurantRepository.findById(id);
    }

    @GetMapping(value = "{id}/menu")
    public List<Meal> getMenu(@PathVariable int id) {
        return mealRepository.getMenu(id, LocalDate.now());
    }

    @GetMapping(value = "{id}/with-menu")
    public Optional<Restaurant> getWithMenu(@PathVariable int id) {
        log.info("get user with menu by id: {}", id);
        return RestaurantRepository.getWithMenu(id);
    }
}
