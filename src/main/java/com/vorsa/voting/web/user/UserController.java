package com.vorsa.voting.web.user;

import com.vorsa.voting.model.User;
import com.vorsa.voting.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserRepository userRepository;

    @GetMapping()
    public List<User> list() {
        log.info("findByLastName: {}", "last");
        return userRepository.findByLastNameContainingIgnoreCase("last");
    }

/*    @GetMapping("/with-votes")
    public User getWithVotes() {
        return super.getWithVotes(authUserId());
    }*/
}
