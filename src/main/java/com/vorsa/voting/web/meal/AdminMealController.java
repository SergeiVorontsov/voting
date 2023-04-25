package com.vorsa.voting.web.meal;

import com.vorsa.voting.model.Meal;
import com.vorsa.voting.repository.MealRepository;
import com.vorsa.voting.service.MealService;
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

import static com.vorsa.voting.util.validation.ValidationUtil.assureIdConsistent;
import static com.vorsa.voting.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = AdminMealController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AdminMealController {
    static final String REST_URL = "/api/admin/restaurants";

    private MealRepository repository;
    private MealService service;

    @GetMapping(value = "/{restaurantId}/menus/{menuId}/meals/{mealId}")
    public Meal get(@PathVariable int restaurantId, @PathVariable int menuId, @PathVariable int mealId, @AuthenticationPrincipal AuthUser authUser) {
        //  log.info("get meal with id={}", mealId);
        return repository.getExistedOrBelonged(authUser.id(), mealId);
    }

    @PostMapping(value = "/{restaurantId}/menus/{menuId}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Meal> createWithLocation(@PathVariable int restaurantId, @PathVariable int menuId, @Valid @RequestBody Meal meal, @AuthenticationPrincipal AuthUser authUser) {
        //  log.info("add {}", meal);
        checkNew(meal);
        Meal created = service.save(meal, menuId, authUser.id());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}/menus/{menuId}/meals/{mealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody Meal meal, @PathVariable int restaurantId,
                       @PathVariable int menuId, @PathVariable int mealId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        //  log.info("update {} for user {}", meal, userId);
        assureIdConsistent(meal, mealId);
        repository.getExistedOrBelonged(userId, mealId);
        service.save(meal, menuId, userId);
    }
}
