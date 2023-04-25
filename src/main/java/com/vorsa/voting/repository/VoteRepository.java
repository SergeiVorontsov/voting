package com.vorsa.voting.repository;

import com.vorsa.voting.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    LocalTime REVOTE_DEADLINE = LocalTime.of(11, 0);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.date = current_date")
    Optional<Vote> getExistedByDate(int userId);

    @Transactional
    default Vote saveWithCheck(Vote vote) {
        Vote result = getExistedByDate(vote.getUser().id()).orElse(save(vote));
        if (result.getRestaurant().id() != vote.getRestaurant().id() && LocalTime.now().isBefore(REVOTE_DEADLINE)) {
            result.setRestaurant(vote.getRestaurant());
            result = save(result);
        }
        return result;
    }
}