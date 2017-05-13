package com.ekreative.hackathon.challenge.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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

    private List<Challenge> completedChallenges;
    private List<Challenge> createdChallenges;
    private List<Rating> ratings;

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
