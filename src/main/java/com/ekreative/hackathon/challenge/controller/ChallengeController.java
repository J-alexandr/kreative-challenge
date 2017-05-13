package com.ekreative.hackathon.challenge.controller;

import com.ekreative.hackathon.challenge.entity.Challenge;
import com.ekreative.hackathon.challenge.entity.Rating;
import com.ekreative.hackathon.challenge.entity.User;
import com.ekreative.hackathon.challenge.service.ChallengeService;
import com.ekreative.hackathon.challenge.service.RatingService;
import com.ekreative.hackathon.challenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/challenge")
public class ChallengeController {
    private final ChallengeService challengeService;
    private final UserService userService;
    private final RatingService ratingService;

    @Autowired
    public ChallengeController(ChallengeService challengeService, UserService userService, RatingService ratingService) {
        this.challengeService = challengeService;
        this.userService = userService;
        this.ratingService = ratingService;
    }

    @GetMapping("/all")
    public Collection<Challenge> getChallenges() {
        return challengeService.findActive();
    }

    @GetMapping("/{challengeId}")
    public Challenge getChallenge(@PathVariable Integer challengeId) {
        return challengeService.findById(challengeId);
    }

    @PostMapping("/add")
    public Challenge createChallenge(@RequestParam String title,
                                     @RequestParam String description,
                                     @RequestParam Double longitude,
                                     @RequestParam Double latitude,
                                     @RequestHeader String UUID) {
        Challenge challenge = new Challenge();
        challenge.setTitle(title);
        challenge.setDescription(description);
        challenge.setCreated(LocalDateTime.now());
        challenge.setHidden(false);
        challenge.setLongitude(longitude);
        challenge.setLatitude(latitude);

        User user = userService.findByUuid(UUID);
        challenge.setCreator(user);

        challengeService.save(challenge);
        return challenge;
    }

    @PostMapping("/{challengeId}/complete")
    public Challenge completeChallenge(@PathVariable Integer challengeId, @RequestHeader String UUID) {
        User user = userService.findByUuid(UUID);
        Challenge challenge = challengeService.findById(challengeId);
        userService.saveCompleteChallenge(user, challenge);
        return challenge;
    }

    @DeleteMapping("/{challengeId}/delete")
    public void hideChallenge(@PathVariable Integer challengeId, @RequestHeader String UUID) {
        User user = userService.findByUuid(UUID);
        Challenge challenge = challengeService.findById(challengeId);
        if (user.equals(challenge.getCreator())) {
            challenge.setHidden(true);
            challengeService.save(challenge);
        }
        // TODO: 13.05.2017 response with status code
    }

    @PostMapping("/{challengeId}/rate")
    public Rating rateChallenge(@PathVariable Integer challengeId, @RequestParam Integer rating, @RequestHeader String UUID) {
        Rating userRating = new Rating();
        userRating.setRate(rating);
        Challenge challenge = challengeService.findById(challengeId);
        userRating.setChallenge(challenge);
        User user = userService.findByUuid(UUID);
        userRating.setUser(user);
        ratingService.save(userRating);
        return userRating;
    }
}
