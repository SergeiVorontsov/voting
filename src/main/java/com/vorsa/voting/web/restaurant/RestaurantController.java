package com.vorsa.voting.web.restaurant;

import com.vorsa.voting.model.Restaurant;
import com.vorsa.voting.repository.RestaurantRepository;
import com.vorsa.voting.util.validation.ValidationUtil;
import com.vorsa.voting.web.AuthUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
// TODO: cache, AbstractRestaurantController
public class RestaurantController {

    static final String REST_URL = "/api/restaurants";

    @Autowired
    private RestaurantRepository repository;

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get {}", id);
        return repository.getExisted(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.findAll();
    }

    @GetMapping("with-menu")
    public List<Restaurant> getAllWithMenu() {
        log.info("get all restaurants with menu");
        return repository.getAllWithMenu();
    }

    @GetMapping("{id}/with-menu")
    public Optional<Restaurant> getWithMenu(@PathVariable int id) {
        log.info("get restaurant with menu by id: {}", id);
        return repository.getWithMenu(id);
    }



    /*   @GetMapping("{id}")
       public Optional<Restaurant> get(@PathVariable int id) {
           log.info("get user by id: {}", id);
           return restaurantRepositoryImp.restaurantRepository.findById(id);
       }

       @GetMapping("{id}/menu")
       public List<Meal> getMenu(@PathVariable int id) {
           log.info("get restaurant menu by id: {}", id);
           return mealRepository.getMenu(id, LocalDate.now());
       }

       @GetMapping("{id}/with-menu")
       public Optional<Restaurant> getWithMenu(@PathVariable int id) {
           log.info("get restaurant with menu by id: {}", id);
           return restaurantRepositoryImp.restaurantRepository.getWithMenu(id, LocalDate.now());
       }
   */

}
