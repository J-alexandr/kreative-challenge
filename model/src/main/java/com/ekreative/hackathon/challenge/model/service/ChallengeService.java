package com.ekreative.hackathon.challenge.model.service;

import com.ekreative.hackathon.challenge.model.entity.Challenge;
import com.ekreative.hackathon.challenge.model.entity.User;
import com.ekreative.hackathon.challenge.model.repository.ChallengeRepository;

import java.util.Collection;

public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final UserService userService;

    public ChallengeService(ChallengeRepository challengeRepository, UserService userService) {
        this.challengeRepository = challengeRepository;
        this.userService = userService;
    }

    public Collection<Challenge> findAllCompletedChallengesByUserId(int userId) {
        Collection<Challenge> challenges = challengeRepository.findAllCompletedChallengesByUserId(userId);
        for (Challenge challenge : challenges) {
            pullAllParticipants(challenge);
        }
        return challenges;
    }

    public Collection<Challenge> findAllChallengesCreatedByUserId(int userId) {
        Collection<Challenge> challenges = challengeRepository.findAllChallengesCreatedByUserId(userId);
        for (Challenge challenge : challenges) {
            pullAllParticipants(challenge);
        }
        return challenges;
    }

    public Collection<Challenge> findActive() {
        Collection<Challenge> challenges = challengeRepository.findActive();
        for (Challenge challenge : challenges) {
            pullAllParticipants(challenge);
        }
        return challenges;
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
