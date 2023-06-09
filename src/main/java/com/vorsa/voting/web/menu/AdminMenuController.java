package com.vorsa.voting.web.menu;

import com.vorsa.voting.model.Menu;
import com.vorsa.voting.repository.MenuRepository;
import com.vorsa.voting.repository.RestaurantRepository;
import com.vorsa.voting.service.MenuService;
import com.vorsa.voting.to.MenuTo;
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
import java.time.LocalDate;
import java.util.List;

import static com.vorsa.voting.util.MenuUtil.createTo;
import static com.vorsa.voting.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Menu", description = "Admin menu management APIs. Admin can manage only menus of restaurants that he created")
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {

    static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final MenuService service;
    private final UniqueDateValidator dateValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(dateValidator);
    }

    @GetMapping(value = "/{restaurantId}/menus/{menuId}")
    @Operation(summary = "Get restaurants menu by id")
    @Transactional
    public MenuTo get(@PathVariable int restaurantId, @PathVariable int menuId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("get menu with id= {} by user with id= {}", menuId, userId);
        restaurantRepository.getExistedOrBelonged(userId, restaurantId);
        return createTo(menuRepository.getExisted(menuId));
    }

    @GetMapping("/{restaurantId}/menus/by-date")
    @Operation(summary = "Get restaurants menu by its date for")
    @Transactional
    public MenuTo getByDate(@PathVariable int restaurantId, @RequestParam LocalDate date, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("get menu of restaurant  with id= {} for the date= {} by user with id= {}", restaurantId, date, userId);
        restaurantRepository.getExistedOrBelonged(userId, restaurantId);
        return createTo(menuRepository.getExistedByDate(restaurantId, date));
    }

    @GetMapping(value = "/{restaurantId}/menus")
    @Operation(summary = "Get all menus of restaurant")
    @Transactional
    public List<Menu> getAll(@PathVariable int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("get all menus of the restaurant with id= {} by user with id= {}", restaurantId, userId);
        restaurantRepository.getExistedOrBelonged(userId, restaurantId);
        return menuRepository.getAll(restaurantId);
    }

    @PostMapping(value = "/{restaurantId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Create new restaurants menu for specified date")
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody Menu menu, @PathVariable int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("create {} for restaurant id= {} by user with id= {}", menu, restaurantId, userId);
        checkNew(menu);
        restaurantRepository.getExistedOrBelonged(userId, restaurantId);
        Menu created = service.save(userId, restaurantId, menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{restaurantId}/menus/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete restaurant menu by id")
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(@PathVariable int restaurantId, @PathVariable int menuId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("delete menu with id= {} by user with id= {}", menuId, userId);
        restaurantRepository.getExistedOrBelonged(userId, restaurantId);
        Menu menu = menuRepository.getExisted(menuId);
        menuRepository.delete(menu);
    }
}
