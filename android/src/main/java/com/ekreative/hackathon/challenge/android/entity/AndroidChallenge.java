package com.ekreative.hackathon.challenge.android.entity;

import com.ekreative.hackathon.challenge.model.entity.Challenge;
import com.ekreative.hackathon.challenge.model.entity.User;
import com.ekreative.hackathon.challenge.model.util.LocalDateTimeUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AndroidChallenge {
    private Integer id;
    private String title;
    private String description;
    private Double longitude;
    private Double latitude;
    private Double averageRating;
    private User creator;
    private Long created;
    private Boolean hidden;

    public AndroidChallenge(Challenge challenge) {
        this.id = challenge.getId();
        this.title = challenge.getTitle();
        this.description = challenge.getDescription();
        this.longitude = challenge.getLongitude();
        this.latitude = challenge.getLatitude();
        this.averageRating = challenge.getAverageRating();
        this.creator = challenge.getCreator();
        this.created = LocalDateTimeUtils.toLong(challenge.getCreated());
    }
}
