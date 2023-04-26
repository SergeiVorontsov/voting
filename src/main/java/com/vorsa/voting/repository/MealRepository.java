package com.vorsa.voting.repository;

import com.vorsa.voting.error.DataConflictException;
import com.vorsa.voting.model.Meal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {

    @Query("SELECT m FROM Meal m WHERE m.id =:id and m.menu.restaurant.user.id =:userId")
    Optional<Meal> get(int id, int userId);

    default Meal getExistedOrBelonged(int userId, int id) {
        return get(id, userId).orElseThrow(
                () -> new DataConflictException("Meal with id=" + id + "  is not exist or doesn't belong to User id=" + userId));
    }
}