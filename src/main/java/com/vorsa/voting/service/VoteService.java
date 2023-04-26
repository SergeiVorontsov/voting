package com.vorsa.voting.service;

import com.vorsa.voting.error.DataConflictException;
import com.vorsa.voting.model.User;
import com.vorsa.voting.model.Vote;
import com.vorsa.voting.repository.RestaurantRepository;
import com.vorsa.voting.repository.VoteRepository;
import com.vorsa.voting.web.AuthUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@AllArgsConstructor
public class VoteService {
    private final LocalTime REVOTE_DEADLINE = LocalTime.of(11, 0);
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Vote save(User user, int restaurantId) {
        Vote vote = new Vote();
        voteRepository.getExistedByDate(AuthUser.authUser().id()).ifPresent(dbVote -> {
            if (LocalTime.now().isBefore(REVOTE_DEADLINE)) return;
            throw new DataConflictException("You have already vote today");
        });
        vote.setUser(user);
        vote.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return voteRepository.save(vote);
    }
}