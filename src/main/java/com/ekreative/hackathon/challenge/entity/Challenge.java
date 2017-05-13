package com.ekreative.hackathon.challenge.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class Challenge extends BasicEntity {
    private String title;
    private String description;
    private Double longitude;
    private Double latitude;
    private Double averageRating;
    private User creator;
    private LocalDateTime created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Challenge challenge = (Challenge) o;
        return Objects.equals(title, challenge.title) &&
                Objects.equals(description, challenge.description) &&
                Objects.equals(longitude, challenge.longitude) &&
                Objects.equals(latitude, challenge.latitude) &&
                Objects.equals(creator, challenge.creator) &&
                Objects.equals(created, challenge.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, longitude, latitude, creator, created);
    }
}
