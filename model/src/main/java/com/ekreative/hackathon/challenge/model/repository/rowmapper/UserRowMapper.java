package com.ekreative.hackathon.challenge.model.repository.rowmapper;

import com.ekreative.hackathon.challenge.model.entity.User;
import com.ekreative.hackathon.challenge.model.util.LocalDateTimeUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUUID(rs.getString("uuid"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEnabled(rs.getBoolean("enabled"));
        if (rs.getLong("user_created") != 0) {
            user.setCreated(LocalDateTimeUtils.toLocalDateTime(rs.getLong("user_created")));
        }

        return user;
    }
}
