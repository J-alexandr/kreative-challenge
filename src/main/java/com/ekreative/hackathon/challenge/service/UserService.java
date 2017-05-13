package com.ekreative.hackathon.challenge.service;

import com.ekreative.hackathon.challenge.entity.Challenge;
import com.ekreative.hackathon.challenge.entity.Rating;
import com.ekreative.hackathon.challenge.entity.User;
import com.ekreative.hackathon.challenge.repository.ChallengeRepository;
import com.ekreative.hackathon.challenge.repository.RatingRepository;
import com.ekreative.hackathon.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final RatingRepository ratingRepository;

    @Autowired
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
