package com.ekreative.hackathon.challenge.service;

import com.ekreative.hackathon.challenge.entity.Rating;
import com.ekreative.hackathon.challenge.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RatingService {
    private final RatingRepository rateRepository;

    @Autowired
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
