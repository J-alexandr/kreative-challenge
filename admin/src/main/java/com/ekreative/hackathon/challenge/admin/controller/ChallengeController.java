package com.ekreative.hackathon.challenge.admin.controller;

import com.ekreative.hackathon.challenge.model.entity.Challenge;
import com.ekreative.hackathon.challenge.model.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping("/panel/challenges")
public class ChallengeController {
    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping("/all")
    public String getAll(Model model, Principal principal) {
        Collection<Challenge> challenges = challengeService.findAll();
        model.addAttribute("challenges", challenges);
        model.addAttribute("username", principal.getName());
        model.addAttribute("title", "All challenges");
        return "challenge/table";
    }

    @GetMapping("/{challengeId}/disable")
    public String disable(@PathVariable Integer challengeId) {
        Challenge challenge = challengeService.findById(challengeId);
        challenge.setHidden(true);
        challengeService.save(challenge);
        return "redirect:../all";
    }

    @GetMapping("/{challengeId}/enable")
    public String enable(@PathVariable Integer challengeId) {
        Challenge challenge = challengeService.findById(challengeId);
        challenge.setHidden(false);
        challengeService.save(challenge);
        return "redirect:../all";
    }

    @GetMapping(value = "/{challengeId}/edit")
    public String edit(@PathVariable Integer challengeId, Model model, Principal principal) {
        Challenge challenge = challengeService.findById(challengeId);
        model.addAttribute("challenge", challenge);
        model.addAttribute("title", "Edit challenge");
        model.addAttribute("username", principal.getName());
        return "challenge/edit";
    }

    @PostMapping("/save")
    public String save(@RequestParam Integer id, @RequestParam String title, @RequestParam String description) {
        Challenge challenge = challengeService.findById(id);
        challenge.setTitle(title);
        challenge.setDescription(description);
        challengeService.save(challenge);
        return "redirect:all";
    }
}
