package com.vorsa.voting.web.restaurant;

import com.vorsa.voting.model.Restaurant;
import com.vorsa.voting.repository.RestaurantRepository;
import com.vorsa.voting.to.RestaurantTo;
import com.vorsa.voting.util.RestaurantUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
// TODO: cache
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    private RestaurantRepository repository;

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant with id={}", id);
        return repository.getExisted(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.findAll();
    }

    @GetMapping("/with-today-menu")
    public List<RestaurantTo> getAllWithMenu() {
        log.info("get all restaurants with menu");
        return RestaurantUtil.getTos(repository.getAllWithMenu());
    }

    @GetMapping("/{id}/with-today-menu")
    public RestaurantTo getWithMenu(@PathVariable int id) {
        log.info("get restaurant with id={} with menu", id);
        return RestaurantUtil.createTo(repository.getExistedWithMenu(id));
    }
}
