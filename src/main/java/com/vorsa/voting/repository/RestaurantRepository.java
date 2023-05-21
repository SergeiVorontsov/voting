package com.vorsa.voting.repository;

import com.vorsa.voting.error.DataConflictException;
import com.vorsa.voting.error.NotFoundException;
import com.vorsa.voting.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menus m WHERE r.id=:id  AND m.date =:date")
    Optional<Restaurant> getWithMenu(int id, LocalDate date);

    @Query("SELECT r FROM Restaurant r  LEFT JOIN FETCH r.menus m WHERE m.date =:date")
    List<Restaurant> getAllWithMenu(LocalDate date);

    @Query("SELECT r FROM Restaurant r WHERE r.user.id=:userId")
    List<Restaurant> getAllBelonged(int userId);

    @Query("SELECT r FROM Restaurant r WHERE r.id = :id and r.user.id = :userId")
    Optional<Restaurant> get(int userId, int id);

    default Restaurant getExistedOrBelonged(int userId, int id) {
        return get(userId, id).orElseThrow(
                () -> new DataConflictException("Restaurant id=" + id + "   is not exist or doesn't belong to User id=" + userId));
    }

    default Restaurant getExistedWithMenu(int id) {
        return getWithMenu(id, LocalDate.now()).orElseThrow(() -> new NotFoundException("Restaurant with id=" + id + " hasn't today menu"));
    }
}