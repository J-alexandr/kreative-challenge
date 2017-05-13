package com.ekreative.hackathon.challenge.controller;

import com.ekreative.hackathon.challenge.entity.User;
import com.ekreative.hackathon.challenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User login(@RequestParam String firstName,
                      @RequestParam String lastName,
                      @RequestHeader("UUID") String UUID) {
        User user = new User();
        user.setUUID(UUID);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCreated(LocalDateTime.now());
        user.setEnabled(true);
        userService.save(user);
        return user;
    }
}
