package com.ekreative.hackathon.challenge.web.controller;

import com.ekreative.hackathon.challenge.model.entity.User;
import com.ekreative.hackathon.challenge.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsersTable(Model model) {
        Collection<User> users = userService.findAll()
                .stream()
                .sorted((o1, o2) -> o2.getCompletedChallenges().size() - o1.getCompletedChallenges().size())
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "user_table";
    }
}
