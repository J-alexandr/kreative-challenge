package com.ekreative.hackathon.challenge.web.controller;

import com.ekreative.hackathon.challenge.model.entity.Challenge;
import com.ekreative.hackathon.challenge.model.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/challenges")
public class ChallengeController {
    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping
    public String getChallenges(Model model) {
        Collection<Challenge> challenges = challengeService.findActive()
                .stream()
                .sorted((o1, o2) -> o2.getParticipants().size() - o1.getParticipants().size())
                .collect(Collectors.toList());
        model.addAttribute("challenges", challenges);
        return "challenge_table";
    }
}
