package com.vorsa.voting.web.restaurant;

import com.vorsa.voting.model.Restaurant;
import com.vorsa.voting.repository.RestaurantRepository;
import com.vorsa.voting.service.RestaurantService;
import com.vorsa.voting.to.RestaurantTo;
import com.vorsa.voting.util.RestaurantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Restaurant", description = "Restaurant getting APIs")
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    private RestaurantRepository repository;
    private RestaurantService service;

    @GetMapping("/{id}")
    @Operation(summary = "Get restaurant by id")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant with id={}", id);
        return repository.getExisted(id);
    }

    @GetMapping
    @Operation(summary = "Get all restaurants")
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.findAll();
    }

    @GetMapping("/with-today-menu")
    @Operation(summary = "Get all restaurants with today menu")
    public List<RestaurantTo> getAllWithMenu() {
        log.info("get all restaurants with today menus");
        return RestaurantUtil.getTos(repository.getAllWithMenu(LocalDate.now()));
    }

    @GetMapping("/{id}/with-today-menu")
    @Operation(summary = "Get restaurant with today menu by id")
    public RestaurantTo getWithMenu(@PathVariable int id) {
        log.info("get restaurant with id={} with menu", id);
        return RestaurantUtil.createTo(repository.getExistedWithMenu(id));
    }

    @GetMapping("/with-today-menu-and-dishes")
    @Cacheable(cacheNames = "restaurants")
    @Operation(summary = "Get all restaurants with today menu and dishes")
    public List<RestaurantTo> getAllWithMenuAndDishes() {
        log.info("get all restaurants with menu and dishes");
        return RestaurantUtil.getTos(service.getAllWithMenuAndDishes());
    }

    @GetMapping("/{id}/with-today-menu-and-dishes")
    @Operation(summary = "Get restaurant with today menu and dishes by id")
    public RestaurantTo getWithMenuAndDishes(@PathVariable int id) {
        log.info("get restaurant with id={} with today menu and dishes", id);
        return RestaurantUtil.createTo(service.getWithMenuAndDishes(id));
    }
}
