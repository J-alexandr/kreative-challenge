package com.ekreative.hackathon.challenge.service;

import com.ekreative.hackathon.challenge.KreativeChallengeApplication;
import com.ekreative.hackathon.challenge.entity.Challenge;
import com.ekreative.hackathon.challenge.entity.Rating;
import com.ekreative.hackathon.challenge.entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

@SpringBootTest
@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("/application.properties")
@ContextConfiguration(classes = {KreativeChallengeApplication.class})
public class RatingServiceTest {
    private final String realUserUuid = "2222-2222-2222-2222";
    private final Integer rateValue = 1;
    private final Integer challengeId = 1;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChallengeService challengeService;

    @Test
    public void testFindAllRatings() {
        Collection<Rating> ratings = ratingService.findAll();
        Assert.assertTrue(ratings.size() > 0);
    }

    @Test
    public void testFindAllUserRatings() {
        User user = userService.findByUuid(realUserUuid);
        Collection<Rating> allUserRatings = ratingService.findAllUserRatings(user.getId());

        Assert.assertTrue(allUserRatings.size() > 0);
    }

    @Test
    public void testAddRating() {
        Rating rating = new Rating();
        rating.setRate(rateValue);
        Challenge challenge = challengeService.findById(challengeId);
        rating.setChallenge(challenge);
        User user = userService.findByUuid(realUserUuid);
        rating.setUser(user);
        ratingService.save(rating);

        Rating ratingFromRepository = ratingService.findUserRatingForChallenge(user.getId(), challenge.getId());
        Assert.assertTrue(ratingFromRepository.getRate().equals(rateValue));
    }

    @After
    public void cleanRepository() {
        try {
            Collection<Rating> ratings = ratingService.findAll();
            for (Rating rating : ratings) {
                if (rating.getUser().getUUID().equals(realUserUuid) && rating.getRate().equals(rateValue)) {
                    ratingService.remove(rating);
                }
            }
        } catch (Exception e) {
            System.err.println("Exception while cleaning repository inside @After of UserRepositoryTest");
        }
    }
}
