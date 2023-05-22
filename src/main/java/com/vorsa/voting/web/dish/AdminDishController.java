package com.vorsa.voting.web.dish;

import com.vorsa.voting.model.Dish;
import com.vorsa.voting.repository.DishRepository;
import com.vorsa.voting.repository.RestaurantRepository;
import com.vorsa.voting.service.DishService;
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
import java.util.List;

import static com.vorsa.voting.util.validation.ValidationUtil.assureIdConsistent;
import static com.vorsa.voting.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Dish", description = "Admin dish management APIs. Admin can managed only dishes of menus of restaurants that he created ")
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {
    static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final DishService service;
    private final UniqueNameValidator nameValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(nameValidator);
    }

    @GetMapping(value = "/{restaurantId}/menus/{menuId}/dishes/{dishId}")
    @Operation(summary = "Get restaurant menu dish by id")
    @Transactional
    public Dish get(@PathVariable int restaurantId, @PathVariable int menuId, @PathVariable int dishId,
                    @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("get dish with id= {} by user with id= {}", dishId, userId);
        restaurantRepository.getExistedOrBelonged(userId, restaurantId);
        return dishRepository.getExisted(dishId);
    }

    @GetMapping(value = "/{restaurantId}/menus/{menuId}/dishes")
    @Operation(summary = "Get all dishes of menu by id")
    @Transactional
    public List<Dish> getAll(@PathVariable int restaurantId, @PathVariable int menuId,
                             @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("get all dishes of menu with id= {} by user with id= {}", menuId, userId);
        restaurantRepository.getExistedOrBelonged(userId, restaurantId);
        return dishRepository.getAll(menuId);
    }

    @PostMapping(value = "/{restaurantId}/menus/{menuId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new dish for specified restaurant menu")
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int restaurantId,
                                                   @PathVariable int menuId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("add {} for the restaurant with id= {} for the menu with id= {} by user with id ={}",
                dish, restaurantId, menuId, userId);
        checkNew(dish);
        restaurantRepository.getExistedOrBelonged(userId, restaurantId);
        Dish created = service.save(menuId, dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}/menus/{menuId}/dishes/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update dish by Id")
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int restaurantId,
                       @PathVariable int menuId, @PathVariable int dishId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("update {} by user with id= {}", dish, userId);
        assureIdConsistent(dish, dishId);
        restaurantRepository.getExistedOrBelonged(userId, restaurantId);
        dishRepository.getExisted(dishId);
        service.save(menuId, dish);
    }

    @DeleteMapping("/{restaurantId}/menus/{menuId}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete dish by id")
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(@PathVariable int restaurantId,
                       @PathVariable int menuId, @PathVariable int dishId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("delete dish with id= {} by user with id= {}", dishId, userId);
        restaurantRepository.getExistedOrBelonged(userId, restaurantId);
        Dish dish = dishRepository.getExisted(dishId);
        dishRepository.delete(dish);
    }
}
