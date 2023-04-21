package com.vorsa.voting.repository;

import com.vorsa.voting.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu m WHERE r.id=:id AND m.publicationDate =:now")
    Optional<Restaurant> getWithMenu(int id, LocalDate now);

    //the last publication date
   /* @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu m WHERE m.publicationDate = (SELECT MAX (publicationDate) from Meal WHERE restaurant.id=r.id )")
    List<Restaurant> getAllWithMenu();*/

    //the today publication date
    @Query("SELECT r FROM Restaurant r  LEFT JOIN FETCH r.menu m WHERE m.publicationDate = current_date")
    List<Restaurant> getAllWithMenu();
}