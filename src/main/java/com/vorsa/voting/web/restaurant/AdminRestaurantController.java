package com.vorsa.voting.web.restaurant;

import com.vorsa.voting.model.Restaurant;
import com.vorsa.voting.repository.RestaurantRepository;
import com.vorsa.voting.service.RestaurantService;
import com.vorsa.voting.web.AuthUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.vorsa.voting.util.validation.ValidationUtil.assureIdConsistent;
import static com.vorsa.voting.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AdminRestaurantController {
    static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantRepository repository;
    private final RestaurantService service;

    @GetMapping
    public List<Restaurant> getAll(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("get all restaurants belong to user with id= {} ", userId);
        return repository.getAllBelonged(userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("create {} for user {}", restaurant, userId);
        checkNew(restaurant);
        Restaurant created = service.save(userId, restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("update {} for user {}", restaurant, userId);
        assureIdConsistent(restaurant, id);
        repository.getExistedOrBelonged(userId, id);
        service.save(userId, restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("delete {} for user {}", id, userId);
        Restaurant restaurant = repository.getExistedOrBelonged(userId, id);
        repository.delete(restaurant);
    }
}
