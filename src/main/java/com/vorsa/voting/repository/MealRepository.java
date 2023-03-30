package com.vorsa.voting.repository;

import com.vorsa.voting.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository <Meal>{
    @Query("SELECT m FROM Meal m JOIN FETCH m.restaurant " +
            "WHERE m.publicationDate=:publicationDate AND m.restaurant.id=:restaurantId ORDER BY m.name ASC")

    List<Meal> getMenu(int restaurantId, LocalDate publicationDate);
}