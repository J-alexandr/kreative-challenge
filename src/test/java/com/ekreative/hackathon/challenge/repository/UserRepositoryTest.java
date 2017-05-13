package com.ekreative.hackathon.challenge.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("/application.properties")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    // TODO: 13.05.2017 test UserRepository
}
