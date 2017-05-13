package com.ekreative.hackathon.challenge.repository.rowmapper;

import com.ekreative.hackathon.challenge.entity.Challenge;
import com.ekreative.hackathon.challenge.entity.Rating;
import com.ekreative.hackathon.challenge.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingRowMapper implements RowMapper<Rating> {
    @Override
    public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
        Rating rating = new Rating();

        UserRowMapper userRowMapper = new UserRowMapper();
        User user = userRowMapper.mapRow(rs, rowNum);
        rating.setUser(user);

        ChallengeRowMapper challengeRowMapper = new ChallengeRowMapper();
        Challenge challenge = challengeRowMapper.mapRow(rs, rowNum);
        rating.setChallenge(challenge);

        rating.setRate(rs.getInt("rate"));
        return rating;
    }
}
