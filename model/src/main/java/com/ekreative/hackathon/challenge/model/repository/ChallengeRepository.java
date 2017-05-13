package com.ekreative.hackathon.challenge.model.repository;

import com.ekreative.hackathon.challenge.model.entity.Challenge;
import com.ekreative.hackathon.challenge.model.repository.exception.EntityNotFoundException;
import com.ekreative.hackathon.challenge.model.repository.rowmapper.ChallengeRowMapper;
import com.ekreative.hackathon.challenge.model.util.LocalDateTimeUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ChallengeRepository implements BasicRepository<Challenge> {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert challengeInsert;

    public ChallengeRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.challengeInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("challenge")
                .usingGeneratedKeyColumns("id");
    }

    public Collection<Challenge> findAllChallengesCreatedByUserId(int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        String sql = "SELECT c.id AS challenge_id, c.title, c.description, c.longitude, c.latitude, c.hidden, " +
                "c.creator_id, c.created AS challenge_created, " +
                "(SELECT AVG(rate) FROM rating WHERE challenge_id=c.id) AS average_rating, " +
                "u.id AS user_id, u.uuid, u.first_name, u.last_name, u.enabled, u.created AS user_created  " +
                "FROM challenge c " +
                "   LEFT JOIN users u ON u.id = c.creator_id " +
                "WHERE c.creator_id=:user_id";

        return this.namedParameterJdbcTemplate.query(
                sql,
                sqlParameterSource,
                new ChallengeRowMapper()
        );
    }

    public Collection<Challenge> findAllCompletedChallengesByUserId(int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        String sql = "SELECT c.id AS challenge_id, c.title, c.description, c.longitude, c.latitude, c.hidden, " +
                "c.creator_id, c.created AS challenge_created, " +
                "(SELECT AVG(rate) FROM rating WHERE challenge_id=c.id) AS average_rating, " +
                "u.id AS user_id, u.uuid, u.first_name, u.last_name, u.enabled, u.created AS user_created  " +
                "FROM complete_challenge cc" +
                "   LEFT JOIN challenge c ON c.id=cc.challenge_id " +
                "   LEFT JOIN users u ON u.id = c.creator_id " +
                "WHERE cc.user_id=:user_id";

        return this.namedParameterJdbcTemplate.query(
                sql,
                sqlParameterSource,
                new ChallengeRowMapper()
        );
    }

    public Collection<Challenge> findActive() {
        String sql = "SELECT c.id AS challenge_id, c.title, c.description, c.longitude, c.latitude, c.hidden, " +
                "c.creator_id, c.created AS challenge_created, " +
                "(SELECT AVG(rate) FROM rating WHERE challenge_id=c.id) AS average_rating, " +
                "u.id AS user_id, u.uuid, u.first_name, u.last_name, u.enabled, u.created AS user_created  " +
                "FROM challenge c " +
                "   LEFT JOIN users u ON u.id = c.creator_id " +
                "WHERE c.hidden=FALSE";

        return this.namedParameterJdbcTemplate.query(
                sql,
                new ChallengeRowMapper()
        );
    }

    @Override
    public Challenge findById(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        String sql = "SELECT c.id AS challenge_id, c.title, c.description, c.longitude, c.latitude, c.hidden, " +
                "c.creator_id, c.created AS challenge_created, " +
                "(SELECT AVG(rate) FROM rating WHERE challenge_id=c.id) AS average_rating, " +
                "u.id AS user_id, u.uuid, u.first_name, u.last_name, u.enabled, u.created AS user_created  " +
                "FROM challenge c " +
                "   LEFT JOIN users u ON u.id = c.creator_id " +
                "WHERE c.id=:id";

        try {
            return this.namedParameterJdbcTemplate.queryForObject(
                    sql,
                    sqlParameterSource,
                    new ChallengeRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Collection<Challenge> findAll() {
        String sql = "SELECT c.id AS challenge_id, c.title, c.description, c.longitude, c.latitude, c.hidden, " +
                "c.creator_id, c.created AS challenge_created, " +
                "(SELECT AVG(rate) FROM rating WHERE challenge_id=c.id) AS average_rating, " +
                "u.id AS user_id, u.uuid, u.first_name, u.last_name, u.enabled, u.created AS user_created  " +
                "FROM challenge c " +
                "   LEFT JOIN users u ON u.id = c.creator_id";

        return this.namedParameterJdbcTemplate.query(
                sql,
                new ChallengeRowMapper()
        );
    }

    @Override
    public void remove(Challenge challenge) {
        String sql = "DELETE FROM challenge WHERE id=:id";
        this.remove(sql, challenge.getId(), namedParameterJdbcTemplate);
    }

    @Override
    public void save(Challenge challenge) {
        if (challenge.isNew()) {
            add(challenge);
        } else {
            update(challenge);
        }
    }

    private void add(Challenge challenge) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", challenge.getTitle());
        params.put("description", challenge.getDescription());
        params.put("longitude", challenge.getLongitude());
        params.put("latitude", challenge.getLatitude());
        params.put("creator_id", challenge.getCreator().getId());
        params.put("hidden", challenge.getHidden());

        if (challenge.getCreated() != null) {
            params.put("created", LocalDateTimeUtils.toLong(challenge.getCreated()));
        } else {
            params.put("created", null);
        }

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        Number id = this.challengeInsert.executeAndReturnKey(sqlParameterSource);
        challenge.setId(id.intValue());
    }

    private void update(Challenge challenge) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", challenge.getId());
        params.put("title", challenge.getTitle());
        params.put("description", challenge.getDescription());
        params.put("longitude", challenge.getLongitude());
        params.put("latitude", challenge.getLatitude());
        params.put("creator_id", challenge.getCreator().getId());
        params.put("hidden", challenge.getHidden());

        if (challenge.getCreated() != null) {
            params.put("created", LocalDateTimeUtils.toLong(challenge.getCreated()));
        } else {
            params.put("created", null);
        }

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        String sql = "UPDATE challenge " +
                "SET title=:title, description=:descrition, longitude=:longitude, latitude=:latitude, " +
                "creator_id=:creator_id, created=:created, hidden=:hidden " +
                "WHERE id=:id";

        this.namedParameterJdbcTemplate.update(
                sql,
                sqlParameterSource
        );
    }
}
