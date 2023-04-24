package com.vorsa.voting.repository;

import com.vorsa.voting.error.DataConflictException;
import com.vorsa.voting.error.NotFoundException;
import com.vorsa.voting.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menu m WHERE r.id=:id  AND m.publicationDate =current_date")
    Optional<Restaurant> getWithMenu(int id);

    @Query("SELECT r FROM Restaurant r  LEFT JOIN FETCH r.menu m WHERE m.publicationDate = current_date")
    List<Restaurant> getAllWithMenu();

    @Query("SELECT r FROM Restaurant r WHERE r.user.id=:userId")
    List<Restaurant> getAllBelonged(int userId);

    @Query("SELECT r FROM Restaurant r WHERE r.id = :id and r.user.id = :userId")
    Optional<Restaurant> get(int userId, int id);

    default Restaurant getExistedOrBelonged(int userId, int id) {
        return get(userId, id).orElseThrow(
                () -> new DataConflictException("Restaurant id=" + id + "   is not exist or doesn't belong to User id=" + userId));
    }

    default Restaurant getExistedWithMenu(int id) {
        return getWithMenu(id).orElseThrow(() -> new NotFoundException("Restaurant with id=" + id + " not found"));
    }
}