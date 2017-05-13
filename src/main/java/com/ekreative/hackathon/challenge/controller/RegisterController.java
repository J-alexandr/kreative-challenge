package com.ekreative.hackathon.challenge.controller;

import com.ekreative.hackathon.challenge.entity.User;
import com.ekreative.hackathon.challenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User register(@RequestParam String firstName,
                      @RequestParam String lastName,
                      @RequestHeader("UUID") String UUID) {
        try {
            User user = new User();
            user.setUUID(UUID);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setCreated(LocalDateTime.now());
            user.setEnabled(true);
            userService.save(user);
            return user;
        } catch (DuplicateKeyException e) {
            User user = userService.findByUuid(UUID);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            userService.save(user);
            return user;
        }
    }
}
