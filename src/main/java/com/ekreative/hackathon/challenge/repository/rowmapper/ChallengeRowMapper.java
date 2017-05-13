package com.ekreative.hackathon.challenge.repository.rowmapper;

import com.ekreative.hackathon.challenge.entity.Challenge;
import com.ekreative.hackathon.challenge.entity.User;
import com.ekreative.hackathon.challenge.util.LocalDateTimeUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChallengeRowMapper implements RowMapper<Challenge> {
    @Override
    public Challenge mapRow(ResultSet rs, int rowNum) throws SQLException {
        Challenge challenge = new Challenge();
        challenge.setId(rs.getInt("challenge_id"));
        challenge.setTitle(rs.getString("title"));
        challenge.setDescription(rs.getString("description"));
        challenge.setLongitude(rs.getDouble("longitude"));
        challenge.setLatitude(rs.getDouble("latitude"));
        challenge.setAverageRating(rs.getDouble("average_rating"));
        challenge.setHidden(rs.getBoolean("hidden"));
        if (rs.getLong("challenge_created") != 0) {
            challenge.setCreated(LocalDateTimeUtils.toLocalDateTime(rs.getLong("challenge_created")));
        }

        UserRowMapper userRowMapper = new UserRowMapper();
        User creator = userRowMapper.mapRow(rs, rowNum);
        challenge.setCreator(creator);
        return challenge;
    }
}
