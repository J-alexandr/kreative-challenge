package com.ekreative.hackathon.challenge.repository;

import com.ekreative.hackathon.challenge.entity.Rating;
import com.ekreative.hackathon.challenge.repository.exception.EntityNotFoundException;
import com.ekreative.hackathon.challenge.repository.rowmapper.RatingRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RateRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert rateInsert;

    public RateRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.rateInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("ratings");
    }

    public Collection<Rating> findAll() {
        String sql = "SELECT\n" +
                "  r.rate,\n" +
                "  u.id                        AS user_id,\n" +
                "  u.uuid,\n" +
                "  u.first_name,\n" +
                "  u.last_name,\n" +
                "  u.enabled,\n" +
                "  u.created                   AS user_created,\n" +
                "  c.id                        AS challenge_id,\n" +
                "  c.title,\n" +
                "  c.description,\n" +
                "  c.longitude,\n" +
                "  c.latitude,\n" +
                "  c.creator_id,\n" +
                "  c.created                   AS challenge_created,\n" +
                "  (SELECT AVG(rate) FROM rating WHERE challenge_id = c.id) AS average_rating,\n" +
                "  u.id                        AS user_id,\n" +
                "  u.uuid,\n" +
                "  u.first_name,\n" +
                "  u.last_name,\n" +
                "  u.enabled,\n" +
                "  u.created                   AS user_created\n" +
                "FROM rating r\n" +
                "  LEFT JOIN challenge c ON c.id=r.challenge_id\n" +
                "  LEFT JOIN users u ON u.id=r.user_id";

        return this.namedParameterJdbcTemplate.query(
                sql,
                new RatingRowMapper()
        );
    }

    public Rating findUserRatingForChallenge(int userId, int challengeId) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("challenge_id", challengeId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        String sql = "SELECT\n" +
                "  r.rate,\n" +
                "  u.id                        AS user_id,\n" +
                "  u.uuid,\n" +
                "  u.first_name,\n" +
                "  u.last_name,\n" +
                "  u.enabled,\n" +
                "  u.created                   AS user_created,\n" +
                "  c.id                        AS challenge_id,\n" +
                "  c.title,\n" +
                "  c.description,\n" +
                "  c.longitude,\n" +
                "  c.latitude,\n" +
                "  c.creator_id,\n" +
                "  c.created                   AS challenge_created,\n" +
                "  (SELECT AVG(rate) FROM rating WHERE challenge_id = c.id) AS average_rating,\n" +
                "  u.id                        AS user_id,\n" +
                "  u.uuid,\n" +
                "  u.first_name,\n" +
                "  u.last_name,\n" +
                "  u.enabled,\n" +
                "  u.created AS user_created\n" +
                "FROM rating r\n" +
                "  LEFT JOIN challenge c ON c.id = r.challenge_id\n" +
                "  LEFT JOIN users u ON u.id = r.user_id\n" +
                "WHERE r.user_id = :user_id AND r.challenge_id = :challenge_id";

        try {
            return this.namedParameterJdbcTemplate.queryForObject(
                    sql,
                    sqlParameterSource,
                    new RatingRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException();
        }
    }

    public Collection<Rating> findAllRatingsForChallenge(int challengeId) {
        Map<String, Object> params = new HashMap<>();
        params.put("challenge_id", challengeId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        String sql = "SELECT\n" +
                "  r.rate,\n" +
                "  u.id                        AS user_id,\n" +
                "  u.uuid,\n" +
                "  u.first_name,\n" +
                "  u.last_name,\n" +
                "  u.enabled,\n" +
                "  u.created                   AS user_created,\n" +
                "  c.id                        AS challenge_id,\n" +
                "  c.title,\n" +
                "  c.description,\n" +
                "  c.longitude,\n" +
                "  c.latitude,\n" +
                "  c.creator_id,\n" +
                "  c.created                   AS challenge_created,\n" +
                "  (SELECT AVG(rate) FROM rating WHERE challenge_id = c.id) AS average_rating,\n" +
                "  u.id                        AS user_id,\n" +
                "  u.uuid,\n" +
                "  u.first_name,\n" +
                "  u.last_name,\n" +
                "  u.enabled,\n" +
                "  u.created AS user_created\n" +
                "FROM rating r\n" +
                "  LEFT JOIN challenge c ON c.id = r.challenge_id\n" +
                "  LEFT JOIN users u ON u.id = r.user_id\n" +
                "WHERE r.challenge_id = :challenge_id";

        return this.namedParameterJdbcTemplate.query(
                sql,
                sqlParameterSource,
                new RatingRowMapper()
        );
    }
}
