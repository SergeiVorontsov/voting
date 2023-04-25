package com.vorsa.voting.repository;

import com.vorsa.voting.error.DataConflictException;
import com.vorsa.voting.model.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Query("SELECT m FROM Menu m WHERE m.id =:id and m.restaurant.user.id =:userId")
    Optional<Menu> get(int id, int userId);

    default Menu getExistedOrBelonged(int userId, int id) {
        return get(id, userId).orElseThrow(
                () -> new DataConflictException("Menu with id=" + id + "  is not exist or doesn't belong to User id=" + userId));
    }
}
