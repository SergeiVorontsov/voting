package com.vorsa.voting.web.vote;

import com.vorsa.voting.model.Vote;
import com.vorsa.voting.web.MatcherFactory;

import java.time.LocalDate;

import static com.vorsa.voting.web.restaurant.RestaurantTestData.adminRestaurant1;
import static com.vorsa.voting.web.restaurant.RestaurantTestData.anotherAdminRestaurant;
import static com.vorsa.voting.web.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");

    private static final int USER_VOTE1_ID = 1;

    private static final Vote userVote1 = new Vote(USER_VOTE1_ID, null, null, LocalDate.now());

    static {
        userVote1.setUser(user);
        userVote1.setRestaurant(adminRestaurant1);
    }

    public static Vote getNew() {
        return new Vote(null, null, LocalDate.now());
    }

    public static Vote getUpdated() {
        return new Vote(USER_VOTE1_ID, user, anotherAdminRestaurant, LocalDate.now());
    }
}