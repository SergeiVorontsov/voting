package com.vorsa.voting.controller;

import com.vorsa.voting.model.User;
import com.vorsa.voting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController extends AbstractController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public List<User> list() {
        log.info("findByLastName: {}", "last");
        return userRepository.findByLastNameContainingIgnoreCase("last");
    }
}
