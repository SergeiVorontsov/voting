package com.vorsa.voting.web.vote;

import com.vorsa.voting.model.Vote;
import com.vorsa.voting.repository.VoteRepository;
import com.vorsa.voting.util.JsonUtil;
import com.vorsa.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.vorsa.voting.web.restaurant.RestaurantTestData.*;
import static com.vorsa.voting.web.user.UserTestData.ADMIN_MAIL;
import static com.vorsa.voting.web.user.UserTestData.USER_MAIL;
import static com.vorsa.voting.web.vote.VoteController.REST_URL;
import static com.vorsa.voting.web.vote.VoteTestData.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    //https://blog.ttulka.com/how-to-test-date-and-time-in-spring-boot/
    @MockBean
    private Clock clock;

    @BeforeEach
    void setupClock() {
        when(clock.getZone()).thenReturn(
                ZoneId.systemDefault());
        when(clock.instant()).thenReturn(LocalDateTime.now()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    @Autowired
    private VoteRepository repository;

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void vote() throws Exception {
        Vote newVote = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + ADMIN_RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(repository.getExisted(newId), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void revoteBeforeDeadline() throws Exception {
        when(clock.instant()).thenReturn(LocalDateTime
                .of(LocalDate.now(), BEFORE_DEADLINE_TIME)
                .atZone(ZoneId.systemDefault())
                .toInstant());
        Vote updated = VoteTestData.getUpdated();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + ADMIN_RESTAURANT2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        updated.setId(newId);
        VOTE_MATCHER.assertMatch(created, updated);
        VOTE_MATCHER.assertMatch(repository.getExisted(newId), updated);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void revoteAfterDeadline() throws Exception {
        when(clock.instant()).thenReturn(LocalDateTime
                .of(LocalDate.now(), AFTER_DEADLINE_TIME)
                .atZone(ZoneId.systemDefault())
                .toInstant());
        Vote updated = VoteTestData.getUpdated();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + ADMIN_RESTAURANT2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void voteWithoutMenu() throws Exception {
        Vote newVote = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + ANOTHER_ADMIN_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isConflict());
    }
}