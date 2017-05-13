package com.ekreative.hackathon.challenge.model;

import com.ekreative.hackathon.challenge.model.repository.ChallengeRepository;
import com.ekreative.hackathon.challenge.model.repository.RatingRepository;
import com.ekreative.hackathon.challenge.model.repository.UserRepository;
import com.ekreative.hackathon.challenge.model.service.ChallengeService;
import com.ekreative.hackathon.challenge.model.service.RatingService;
import com.ekreative.hackathon.challenge.model.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@SuppressWarnings("all")
public class ModelConfig {

    @Bean
    public ChallengeRepository challengeRepository(DataSource dataSource) {
        return new ChallengeRepository(dataSource);
    }

    @Bean
    public RatingRepository ratingRepository(DataSource dataSource) {
        return new RatingRepository(dataSource);
    }

    @Bean
    public UserRepository userRepository(DataSource dataSource) {
        return new UserRepository(dataSource);
    }

    @Bean
    public ChallengeService challengeService(ChallengeRepository challengeRepository, UserService userService) {
        return new ChallengeService(challengeRepository, userService);
    }

    @Bean
    public RatingService ratingService(RatingRepository ratingRepository) {
        return new RatingService(ratingRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository, ChallengeRepository challengeRepository, RatingRepository ratingRepository) {
        return new UserService(userRepository, challengeRepository, ratingRepository);
    }
}
