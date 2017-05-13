package com.ekreative.hackathon.challenge.model.repository;

import com.ekreative.hackathon.challenge.model.ModelConfig;
import com.ekreative.hackathon.challenge.model.entity.User;
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

@SpringBootTest
@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("/application.properties")
@ContextConfiguration(classes = {ModelConfig.class})
public class UserRepositoryTest {
    private final String UUID = "1111-1111-1111-1111";
    private final String realUserUuid = "2222-2222-2222-2222";
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUUID(UUID);
        user.setFirstName("JUnit");
        user.setLastName("Jawovsky");
        user.setCreated(LocalDateTime.now());

        user.setEnabled(true);
        this.userRepository.save(user);
        User userFromRepository = this.userRepository.findById(user.getId());
        Assert.assertEquals(user, userFromRepository);
    }

    @After
    public void cleanRepository() {
        try {
            User user = userRepository.findByUuid(UUID);
            if (user != null) {
                userRepository.remove(user);
            }
        } catch (Exception e) {
            System.err.println("Exception while cleaning repository inside @After of UserRepositoryTest");
        }
    }
}
