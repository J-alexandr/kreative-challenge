package com.ekreative.hackathon.challenge.model.service;

import com.ekreative.hackathon.challenge.model.entity.Challenge;
import com.ekreative.hackathon.challenge.model.entity.Rating;
import com.ekreative.hackathon.challenge.model.entity.User;
import com.ekreative.hackathon.challenge.model.repository.ChallengeRepository;
import com.ekreative.hackathon.challenge.model.repository.RatingRepository;
import com.ekreative.hackathon.challenge.model.repository.UserRepository;

import java.util.Collection;

public class UserService {
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final RatingRepository ratingRepository;

    public UserService(UserRepository userRepository, ChallengeRepository challengeRepository, RatingRepository ratingRepository) {
        // TODO: 13.05.2017 is repositories or services best practice?
        this.userRepository = userRepository;
        this.challengeRepository = challengeRepository;
        this.ratingRepository = ratingRepository;
    }

    public Collection<User> findAllUsersWhoCompleteChallenge(int challengeId) {
        return userRepository.findAllUsersWhoCompleteChallenge(challengeId);
    }

    public User findByUuid(String UUID) {
        User user = userRepository.findByUuid(UUID);
        pullCompletedChallengesByUser(user);
        pullCreatedChallengesByUser(user);
        pullUserRatings(user);
        return user;
    }

    public User findById(int id) {
        User user = userRepository.findById(id);
        pullCompletedChallengesByUser(user);
        pullCreatedChallengesByUser(user);
        pullUserRatings(user);
        return user;
    }

    public Collection<User> findAll() {
        Collection<User> users = userRepository.findAll();
        for (User user : users) {
            pullCompletedChallengesByUser(user);
            pullCreatedChallengesByUser(user);
            pullUserRatings(user);
        }
        return users;
    }

    public void remove(User user) {
        userRepository.remove(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void saveCompleteChallenge(User user, Challenge challenge) {
        userRepository.saveCompleteChallenge(user, challenge);
    }

    private void pullCompletedChallengesByUser(User user) {
        Collection<Challenge> allCompletedChallengesByUserId = challengeRepository.findAllCompletedChallengesByUserId(user.getId());
        for (Challenge challenge : allCompletedChallengesByUserId) {
            user.addCompletedChallenge(challenge);
        }
    }

    private void pullCreatedChallengesByUser(User user) {
        Collection<Challenge> allCompletedChallengesByUserId = challengeRepository.findAllChallengesCreatedByUserId(user.getId());
        for (Challenge challenge : allCompletedChallengesByUserId) {
            user.addCreatedChallenge(challenge);
        }
    }

    private void pullUserRatings(User user) {
        Collection<Rating> allUserRatings = ratingRepository.findAllUserRatings(user.getId());
        for (Rating rating : allUserRatings) {
            user.addRating(rating);
        }
    }
}
