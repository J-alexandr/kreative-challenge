package com.ekreative.hackathon.challenge.model.service;

import com.ekreative.hackathon.challenge.model.entity.Rating;
import com.ekreative.hackathon.challenge.model.repository.RatingRepository;

import java.util.Collection;

public class RatingService {
    private final RatingRepository rateRepository;

    public RatingService(RatingRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public Collection<Rating> findAll() {
        return rateRepository.findAll();
    }

    public Collection<Rating> findAllUserRatings(int userId) {
        return rateRepository.findAllUserRatings(userId);
    }

    public Rating findUserRatingForChallenge(int userId, int challengeId) {
        return rateRepository.findUserRatingForChallenge(userId, challengeId);
    }

    public Collection<Rating> findAllRatingsForChallenge(int challengeId) {
        return rateRepository.findAllRatingsForChallenge(challengeId);
    }

    public void remove(Rating rating) {
        rateRepository.remove(rating);
    }

    public void save(Rating rating) {
        rateRepository.save(rating);
    }
}
