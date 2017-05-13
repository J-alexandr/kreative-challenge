package com.ekreative.hackathon.challenge.service;

import com.ekreative.hackathon.challenge.entity.Rating;
import com.ekreative.hackathon.challenge.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RatingService {
    private final RateRepository rateRepository;

    @Autowired
    public RatingService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public Collection<Rating> findAll() {
        return rateRepository.findAll();
    }

    public Rating findUserRatingForChallenge(int userId, int challengeId) {
        return rateRepository.findUserRatingForChallenge(userId, challengeId);
    }

    public Collection<Rating> findAllRatingsForChallenge(int challengeId) {
        return rateRepository.findAllRatingsForChallenge(challengeId);
    }
}
