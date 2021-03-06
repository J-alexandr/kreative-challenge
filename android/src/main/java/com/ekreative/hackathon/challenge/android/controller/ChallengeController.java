package com.ekreative.hackathon.challenge.android.controller;

import com.ekreative.hackathon.challenge.android.entity.AndroidChallenge;
import com.ekreative.hackathon.challenge.model.entity.Challenge;
import com.ekreative.hackathon.challenge.model.entity.Rating;
import com.ekreative.hackathon.challenge.model.entity.User;
import com.ekreative.hackathon.challenge.model.service.ChallengeService;
import com.ekreative.hackathon.challenge.model.service.RatingService;
import com.ekreative.hackathon.challenge.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

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
    public Collection<AndroidChallenge> getChallenges() {
        Collection<Challenge> challenges = challengeService.findActive();
        Collection<AndroidChallenge> androidChallenges = new ArrayList<>();
        for (Challenge challenge : challenges) {
            AndroidChallenge androidChallenge = decorateChallenge(challenge);
            androidChallenges.add(androidChallenge);
        }
        return androidChallenges.stream()
                .sorted(Comparator.comparingDouble(AndroidChallenge::getAverageRating).reversed())
                .collect(Collectors.toList());
    }

    @GetMapping("/{challengeId}")
    public AndroidChallenge getChallenge(@PathVariable Integer challengeId) {
        return decorateChallenge(challengeService.findById(challengeId));
    }

    @PostMapping("/add")
    public AndroidChallenge createChallenge(@RequestParam String title,
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
        return decorateChallenge(challenge);
    }

    @PostMapping("/{challengeId}/complete")
    public AndroidChallenge completeChallenge(@PathVariable Integer challengeId, @RequestHeader String UUID) {
        User user = userService.findByUuid(UUID);
        Challenge challenge = challengeService.findById(challengeId);
        userService.saveCompleteChallenge(user, challenge);
        return decorateChallenge(challenge);
    }

    @DeleteMapping("/{challengeId}/delete")
    public void hideChallenge(@PathVariable Integer challengeId, @RequestHeader String UUID) {
        User user = userService.findByUuid(UUID);
        Challenge challenge = challengeService.findById(challengeId);
        if (user.equals(challenge.getCreator())) {
            challenge.setHidden(true);
            challengeService.save(challenge);
        }
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

    private AndroidChallenge decorateChallenge(Challenge challenge) {
        return new AndroidChallenge(challenge);
    }
}
