package com.ekreative.hackathon.challenge.service;

import com.ekreative.hackathon.challenge.KreativeChallengeApplication;
import com.ekreative.hackathon.challenge.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("/application.properties")
@ContextConfiguration(classes = {KreativeChallengeApplication.class})
public class UserServiceTest {
    private final String realUserUuid = "2222-2222-2222-2222";
    @Autowired
    private UserService userService;

    @Test
    public void testFindUserByUUIDAndCheckForCreatedChallenges() {
        User user = userService.findByUuid(realUserUuid);
        Assert.assertTrue(user.getCreatedChallenges().size() > 0);
    }
}
