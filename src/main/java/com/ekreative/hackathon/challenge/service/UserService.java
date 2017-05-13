package com.ekreative.hackathon.challenge.service;

import com.ekreative.hackathon.challenge.entity.Challenge;
import com.ekreative.hackathon.challenge.entity.User;
import com.ekreative.hackathon.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> findAllUsersWhoCompleteChallenge(int challengeId) {
        return userRepository.findAllUsersWhoCompleteChallenge(challengeId);
    }

    public User findByUuid(String UUID) {
        User user = userRepository.findByUuid(UUID);
        List<Challenge> completedChallenges = user.getCompletedChallenges();

        return user;
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }

    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    public void remove(User user) {
        userRepository.remove(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
