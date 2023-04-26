package com.vorsa.voting.web.vote;

import com.vorsa.voting.service.VoteService;
import com.vorsa.voting.to.VoteTo;
import com.vorsa.voting.util.VoteUtil;
import com.vorsa.voting.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String REST_URL = "/api/votes";

    private final VoteService service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<VoteTo> createWithLocation(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("create vote for restaurant id= {} by user {}", restaurantId, authUser.id());
        VoteTo created = VoteUtil.createTo(service.save(authUser.getUser(), restaurantId));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
