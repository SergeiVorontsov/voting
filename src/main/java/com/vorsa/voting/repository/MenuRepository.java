package com.vorsa.voting.repository;

import com.vorsa.voting.error.DataConflictException;
import com.vorsa.voting.model.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Query("SELECT m FROM Menu m WHERE m.date = :date AND m.restaurant.id =:restaurantId")
    Optional<Menu> getByDate(int restaurantId, LocalDate date);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id =:restaurantId and m.restaurant.user.id =:userId")
    List<Menu> getAll(int restaurantId, int userId);

    default Menu getExistedByDate(int restaurantId, LocalDate date) {
        return getByDate(restaurantId, date).orElseThrow(
                () -> new DataConflictException("Menu for the date " + date + " for the restaurant" + restaurantId
                        + " is not exist"));
    }

    default List<Menu> getAllExistedOrBelonged(int userId, int restaurantId) {
        return getAll(restaurantId, userId);
    }
}
