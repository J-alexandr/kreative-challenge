package com.ekreative.hackathon.challenge.service;

import com.ekreative.hackathon.challenge.KreativeChallengeApplication;
import com.ekreative.hackathon.challenge.entity.Challenge;
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

import java.time.LocalDateTime;
import java.util.Collection;

@SpringBootTest
@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("/application.properties")
@ContextConfiguration(classes = {KreativeChallengeApplication.class})
public class ChallengeServiceTest {
    private final String realUserUuid = "2222-2222-2222-2222";
    private final String testChallengedescription = "JUnit test challenge";
    @Autowired
    private ChallengeService challengeService;
    @Autowired
    private UserService userService;
    
    @Test
    public void testFindAllChallenges() {
        Collection<Challenge> allChallenges = challengeService.findAll();
        Assert.assertTrue(allChallenges.size() > 0);
    }

    @Test
    public void testAddNewChallenge() {
        User user = userService.findByUuid(realUserUuid);
        Challenge challenge = new Challenge();
        challenge.setCreated(LocalDateTime.now());
        challenge.setHidden(false);
        challenge.setLatitude(-1.0);
        challenge.setLongitude(2.0);
        challenge.setCreator(user);
        challenge.setTitle(testChallengedescription);
        challenge.setDescription("JUnit test description");
        challengeService.save(challenge);

        Challenge challengeFromRepository = challengeService.findById(challenge.getId());
        Assert.assertEquals(challenge, challengeFromRepository);
    }

    @After
    public void cleanRepository() {
        try {
            Collection<Challenge> challenges = challengeService.findAll();
            for (Challenge challenge : challenges) {
                if (challenge.getTitle().equals(testChallengedescription))
                challengeService.remove(challenge);
            }
        } catch (Exception e) {
            System.err.println("Exception while cleaning repository inside @After of UserRepositoryTest");
        }
    }
}
