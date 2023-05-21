package com.vorsa.voting.repository;

import com.vorsa.voting.model.Meal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {

    @Query("SELECT m FROM Meal m WHERE m.menu.id =:menuId and m.menu.restaurant.user.id =:userId")
    List<Meal> getAll(int userId, int menuId);

    @Query("SELECT m FROM Meal m WHERE m.name = LOWER(:name) AND m.menu.id =:menuId")
    Optional<Meal> findByNameAndMenuIgnoreCase(String name, int menuId);
}