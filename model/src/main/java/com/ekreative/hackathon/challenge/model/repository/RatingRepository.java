package com.ekreative.hackathon.challenge.model.repository;

import com.ekreative.hackathon.challenge.model.entity.Rating;
import com.ekreative.hackathon.challenge.model.repository.exception.EntityNotFoundException;
import com.ekreative.hackathon.challenge.model.repository.rowmapper.RatingRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RatingRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert rateInsert;

    public RatingRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.rateInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("ratings");
    }

    public Collection<Rating> findAllUserRatings(int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
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
                "  c.hidden,\n" +
                "  (SELECT AVG(rate) FROM rating WHERE challenge_id = c.id) AS average_rating,\n" +
                "  u.id                        AS user_id,\n" +
                "  u.uuid,\n" +
                "  u.first_name,\n" +
                "  u.last_name,\n" +
                "  u.enabled,\n" +
                "  u.created                   AS user_created\n" +
                "FROM rating r\n" +
                "  LEFT JOIN challenge c ON c.id=r.challenge_id\n" +
                "  LEFT JOIN users u ON u.id=r.user_id\n" +
                "WHERE r.user_id=:user_id";

        return this.namedParameterJdbcTemplate.query(
                sql,
                sqlParameterSource,
                new RatingRowMapper()
        );
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
                "  c.hidden,\n" +
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
                "  c.hidden,\n" +
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
                "  c.hidden,\n" +
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

    public void remove(Rating rating) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", rating.getUser().getId());
        params.put("challenge_id", rating.getChallenge().getId());
        String sql = "DELETE FROM challenge WHERE user_id=:user_id AND challenge_id=:challenge_id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);

        namedParameterJdbcTemplate.update(
                sql,
                sqlParameterSource
        );
    }

    public void save(Rating rating) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", rating.getUser().getId());
        params.put("challenge_id", rating.getChallenge().getId());
        params.put("rate", rating.getRate());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        String sql = "INSERT INTO rating (user_id, challenge_id, rate) VALUES (:user_id, :challenge_id, :rate) ON CONFLICT DO NOTHING";

        this.namedParameterJdbcTemplate.update(
                sql,
                sqlParameterSource
        );
    }
}
