package com.ekreative.hackathon.challenge.service;

import com.ekreative.hackathon.challenge.entity.Challenge;
import com.ekreative.hackathon.challenge.entity.User;
import com.ekreative.hackathon.challenge.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final UserService userService;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository, UserService userService) {
        this.challengeRepository = challengeRepository;
        this.userService = userService;
    }

    public Collection<Challenge> findAllCompletedChallengesByUserId(int userId) {
        return challengeRepository.findAllCompletedChallengesByUserId(userId);
    }

    public Collection<Challenge> findAllChallengesCreatedByUserId(int userId) {
        return challengeRepository.findAllChallengesCreatedByUserId(userId);
    }

    public Collection<Challenge> findActive() {
        return challengeRepository.findActive();
    }

    public Challenge findById(int id) {
        Challenge challenge = challengeRepository.findById(id);
        pullAllParticipants(challenge);
        return challenge;
    }

    public Collection<Challenge> findAll() {
        Collection<Challenge> challenges = challengeRepository.findAll();
        for (Challenge challenge : challenges) {
            pullAllParticipants(challenge);
        }
        return challenges;
    }

    public void remove(Challenge challenge) {
        challengeRepository.remove(challenge);
    }

    public void save(Challenge challenge) {
        challengeRepository.save(challenge);
    }

    private void pullAllParticipants(Challenge challenge) {
        Collection<User> allUsersWhoCompleteChallenge = userService.findAllUsersWhoCompleteChallenge(challenge.getId());
        for (User user : allUsersWhoCompleteChallenge) {
            challenge.addParticipant(user);
        }
    }
}
