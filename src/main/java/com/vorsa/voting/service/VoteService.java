package com.vorsa.voting.service;

import com.vorsa.voting.model.User;
import com.vorsa.voting.model.Vote;
import com.vorsa.voting.repository.RestaurantRepository;
import com.vorsa.voting.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Vote save(int restaurantId, User user) {
        Vote vote = new Vote();
        vote.setUser(user);
        vote.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return voteRepository.saveWithCheck(vote);
    }
}