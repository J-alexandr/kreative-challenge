package com.ekreative.hackathon.challenge.service;

import com.ekreative.hackathon.challenge.entity.Challenge;
import com.ekreative.hackathon.challenge.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    public Collection<Challenge> findAllCompletedChallengesByUserId(int userId) {
        return challengeRepository.findAllCompletedChallengesByUserId(userId);
    }

    public Challenge findById(int id) {
        return challengeRepository.findById(id);
    }

    public Collection<Challenge> findAll() {
        return challengeRepository.findAll();
    }

    public void remove(Challenge challenge) {
        challengeRepository.remove(challenge);
    }

    public void save(Challenge challenge) {
        challengeRepository.save(challenge);
    }
}
