package com.vorsa.voting.web.meal;

import com.vorsa.voting.model.Meal;
import com.vorsa.voting.repository.MealRepository;
import com.vorsa.voting.service.MealService;
import com.vorsa.voting.web.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.vorsa.voting.util.validation.ValidationUtil.assureIdConsistent;
import static com.vorsa.voting.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Meal", description = "Admin meal management APIs. Admin can managed only meals of menus of restaurants that he created ")
@RequestMapping(value = AdminMealController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMealController {
    static final String REST_URL = "/api/admin/restaurants";

    private final MealRepository repository;
    private final MealService service;
    private final UniqueNameValidator nameValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(nameValidator);
    }

    @GetMapping(value = "/{restaurantId}/menus/{menuId}/meals/{mealId}")
    @Operation(summary = "Get restaurant menu meal by id")
    public Meal get(@PathVariable int restaurantId, @PathVariable int menuId, @PathVariable int mealId,
                    @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("get meal with id= {} by user with id= {}", mealId, userId);
        return repository.getExistedOrBelonged(mealId, userId);
    }

    @PostMapping(value = "/{restaurantId}/menus/{menuId}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new meal for specified restaurant menu")
    @CacheEvict(value = "restaurants", allEntries = true)
    public ResponseEntity<Meal> createWithLocation(@Valid @RequestBody Meal meal, @PathVariable int restaurantId,
                                                   @PathVariable int menuId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("add {} for thr restaurant with id= {} for the menu with id= {} by user with id ={}",
                meal, restaurantId, menuId, userId);
        checkNew(meal);
        Meal created = service.save(userId, menuId, meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}/menus/{menuId}/meals/{mealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update meal by Id")
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(@Valid @RequestBody Meal meal, @PathVariable int restaurantId,
                       @PathVariable int menuId, @PathVariable int mealId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("update {} by user with id= {}", meal, userId);
        assureIdConsistent(meal, mealId);
        repository.getExistedOrBelonged(mealId, userId);
        service.save(userId, menuId, meal);
    }

    @DeleteMapping("/{restaurantId}/menus/{menuId}/meals/{mealId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete meal by id")
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(@PathVariable int restaurantId,
                       @PathVariable int menuId, @PathVariable int mealId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("delete meal with id= {} by user with id= {}", mealId, userId);
        Meal meal = repository.getExistedOrBelonged(mealId, userId);
        repository.delete(meal);
    }
}
