package com.vorsa.voting.web.user;

import com.vorsa.voting.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "users")
// TODO: cache, all endpoints
public class UserController {
    static final String REST_URL = "/api/users";

    private UserRepository userRepository;

}
