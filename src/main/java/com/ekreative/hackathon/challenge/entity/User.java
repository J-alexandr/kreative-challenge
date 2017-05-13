package com.ekreative.hackathon.challenge.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class User extends BasicEntity {
    private String UUID;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    private LocalDateTime created;

    private List<Challenge> completedChallenges = new ArrayList<>();
    private List<Challenge> createdChallenges = new ArrayList<>();
    private List<Rating> ratings = new ArrayList<>();

    public void addCompletedChallenge(Challenge challenge) {
        if (!completedChallenges.contains(challenge)) {
            completedChallenges.add(challenge);
        }
    }

    public void addCreatedChallenge(Challenge challenge) {
        if (!createdChallenges.contains(challenge)) {
            createdChallenges.add(challenge);
        }
    }

    public void addRating(Rating rating) {
        if (!ratings.contains(rating)) {
            ratings.add(rating);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(UUID, user.UUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UUID);
    }
}
