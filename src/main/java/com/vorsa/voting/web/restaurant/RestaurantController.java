package com.vorsa.voting.web.restaurant;

import com.vorsa.voting.model.Meal;
import com.vorsa.voting.model.Restaurant;
import com.vorsa.voting.repository.MealRepository;
import com.vorsa.voting.repository.RestaurantRepository;
import com.vorsa.voting.util.validation.ValidationUtil;
import com.vorsa.voting.web.user.AdminUserController;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
// TODO: cache, update(), delete()
public class RestaurantController {

    static final String REST_URL = "/api/restaurants";

    private RestaurantRepository restaurantRepository;
    private MealRepository mealRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
    public Optional<Restaurant> getWithMenu(@PathVariable int id) {
        log.info("get restaurant with menu by id: {}", id);
        return restaurantRepository.getWithMenu(id, LocalDate.now());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("add new restaurant {}", restaurant);
        ValidationUtil.checkNew(restaurant);
        restaurant = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL +"/{id}")
                .buildAndExpand(restaurant.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(restaurant);
    }
}
