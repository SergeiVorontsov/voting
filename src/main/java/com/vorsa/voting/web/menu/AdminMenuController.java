package com.vorsa.voting.web.menu;

import com.vorsa.voting.model.Menu;
import com.vorsa.voting.repository.MenuRepository;
import com.vorsa.voting.service.MenuService;
import com.vorsa.voting.web.AuthUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.vorsa.voting.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AdminMenuController {

    static final String REST_URL = "/api/admin/restaurants";

    private MenuRepository repository;
    private MenuService service;

    @GetMapping(value = "{restaurantId}/menus/{menuId}")
    public Menu get(@PathVariable int menuId, @AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId) {
        log.info("get menu with id={}", menuId);
        return repository.getExistedOrBelonged(menuId, authUser.id());
    }

    @PostMapping(value = "{restaurantId}/menus",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody Menu menu, @PathVariable int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("create {} for restaurant id= {} for user {}", menu, restaurantId, userId);
        checkNew(menu);

        Menu created = service.save(restaurantId, userId, menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

/*    @GetMapping(value = "/{id}/menus")
    public List<Menu> getAll(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("get menu with id={}", id);
        return repository.getAllExistedOrBelonged(authUser.id(), id);
    }


    @GetMapping("/{id}/menus/by-date")
    public Menu getByDate(@PathVariable int id, @RequestParam LocalDate date, @AuthenticationPrincipal AuthUser authUser) {
        log.info("get menu of restaurant  with id= {} for the date {}", id, date);
        return repository.getExistedOrBelongedByDate(authUser.id(), id, date);
    }*/



/*    @PutMapping(value = "/menus/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody Menu menu, @PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("update {} by user {}", menu, userId);
        assureIdConsistent(menu, id);
        int restaurantId = repository.getExistedOrBelonged(userId, id).getRestaurant().id();
        service.save(userId, restaurantId, menu);
    }*/

/*    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId) {
        log.info("delete {} for user {}", restaurantId, authUser.id());
        Menu menu = repository.getExistedOrBelonged(authUser.id(), restaurantId);
        repository.delete(menu);
    }*/
}
