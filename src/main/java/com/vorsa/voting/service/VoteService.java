package com.vorsa.voting.service;

import com.vorsa.voting.error.DataConflictException;
import com.vorsa.voting.model.User;
import com.vorsa.voting.model.Vote;
import com.vorsa.voting.repository.RestaurantRepository;
import com.vorsa.voting.repository.VoteRepository;
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
        voteRepository.getExistedByDate(user.id()).ifPresent(dbVote -> {
            if (LocalTime.now().isBefore(REVOTE_DEADLINE)){
                vote.setId(dbVote.getId());
                return;}
            throw new DataConflictException("You have already vote today");
        });
        restaurantRepository.getWithMenu(restaurantId).ifPresentOrElse(vote::setRestaurant,()->{
            throw new DataConflictException("Now you can't vote for the restaurant with id="+ restaurantId +", as it doesn't have a today menu yet. Try agan later");
        });
        vote.setUser(user);
        return voteRepository.save(vote);
    }
}