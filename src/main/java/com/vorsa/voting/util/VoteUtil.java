package com.vorsa.voting.util;

import com.vorsa.voting.model.Vote;
import com.vorsa.voting.to.VoteTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VoteUtil {

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getRestaurant().id(), vote.getDate());
    }
}