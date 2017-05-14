package com.ekreative.hackathon.challenge.admin.controller;

import com.ekreative.hackathon.challenge.model.entity.User;
import com.ekreative.hackathon.challenge.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping("/panel/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public String getAll(Model model, Principal principal) {
        Collection<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("username", principal.getName());
        model.addAttribute("title", "All users");
        return "user/table";
    }

    @GetMapping("/{userId}/disable")
    public String disable(@PathVariable Integer userId) {
        User user = userService.findById(userId);
        user.setEnabled(false);
        userService.save(user);
        return "redirect:../all";
    }

    @GetMapping("/{userId}/enable")
    public String enable(@PathVariable Integer userId) {
        User user = userService.findById(userId);
        user.setEnabled(true);
        userService.save(user);
        return "redirect:../all";
    }
}
