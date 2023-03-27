package com.vorsa.voting.repository;

import com.vorsa.voting.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu m WHERE r.id=?1 AND m.publicationDate =?2")
    Optional<Restaurant> getWithMenu(int id, LocalDate now);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu m WHERE m.publicationDate = (SELECT MAX (publicationDate) from Meal WHERE restaurant.id=r.id )")
    List<Restaurant> getAllWithMenu();
}