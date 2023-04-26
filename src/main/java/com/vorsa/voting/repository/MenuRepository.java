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

    @Query("SELECT m FROM Menu m WHERE m.id =:menuId and m.restaurant.user.id =:userId")
    Optional<Menu> get(int menuId, int userId);

    @Query("SELECT m FROM Menu m WHERE m.date = :date AND m.restaurant.id =:restaurantId and m.restaurant.user.id =:userId")
    Optional<Menu> getByDate(int restaurantId, int userId, LocalDate date);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id =:restaurantId and m.restaurant.user.id =:userId")
    List<Menu> getAll(int restaurantId, int userId);

    default Menu getExistedOrBelonged(int userId, int menuId) {
        return get(menuId, userId).orElseThrow(
                () -> new DataConflictException("Menu with id=" + menuId + "  is not exist or doesn't belong to User id=" + userId));
    }

    default Menu getExistedOrBelongedByDate(int userId, int restaurantId, LocalDate date) {
        return getByDate(restaurantId, userId, date).orElseThrow(
                () -> new DataConflictException("Menu for the date " + date + " for the restaurant" + restaurantId
                        + " is not exist or doesn't belong to User id=" + userId));
    }

    default List<Menu> getAllExistedOrBelonged(int userId, int restaurantId) {
        return getAll(restaurantId, userId);
    }
}
