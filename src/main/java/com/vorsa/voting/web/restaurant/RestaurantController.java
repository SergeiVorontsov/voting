package com.vorsa.voting.web.restaurant;

import com.vorsa.voting.model.Meal;
import com.vorsa.voting.model.Restaurant;
import com.vorsa.voting.repository.MealRepository;
import com.vorsa.voting.repository.RestaurantRepository;
import com.vorsa.voting.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/restaurants")
@AllArgsConstructor
@Slf4j
public class RestaurantController {

    private RestaurantRepository restaurantRepository;
    private MealRepository mealRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantRepository.findAll();
    }

    @GetMapping("with-menu")
    public List<Restaurant> getAllWithMenu() {
        log.info("get all restaurant with menu");
        return restaurantRepository.getAllWithMenu();
    }

    @GetMapping("{id}")
    public Optional<Restaurant> get(@PathVariable int id) {
        log.info("get user by id: {}", id);
        return restaurantRepository.findById(id);
    }

    @GetMapping("{id}/menu")
    public List<Meal> getMenu(@PathVariable int id) {
        log.info("get restaurant menu by id: {}", id);
        return mealRepository.getMenu(id, LocalDate.now());
    }

    @GetMapping("{id}/with-menu")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Optional<Restaurant> getWithMenu(@PathVariable int id) {
        log.info("get restaurant with menu by id: {}", id);
        return restaurantRepository.getWithMenu(id, LocalDate.now());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Restaurant> add(@Valid @RequestBody Restaurant restaurant) {
        log.info("add new restaurant {}", restaurant);
        ValidationUtil.checkNew(restaurant);
        restaurant = restaurantRepository.save(restaurant);
        URI uriOfNewResourse = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/restaurant/{id}")
                .buildAndExpand(restaurant.id()).toUri();
        return ResponseEntity.created(uriOfNewResourse).body(restaurant);
    }
}
