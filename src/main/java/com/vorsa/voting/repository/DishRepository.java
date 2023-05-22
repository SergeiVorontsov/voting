package com.vorsa.voting.repository;

import com.vorsa.voting.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT m FROM Dish m WHERE m.menu.id =:menuId")
    List<Dish> getAll(int menuId);

    @Query("SELECT m FROM Dish m WHERE m.name = LOWER(:name) AND m.menu.id =:menuId")
    Optional<Dish> findByNameAndMenuIgnoreCase(String name, int menuId);
}