package com.vorsa.voting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("votes")
public class VoteController {
    @GetMapping
    public String list() {
        return "index";
    }
}
