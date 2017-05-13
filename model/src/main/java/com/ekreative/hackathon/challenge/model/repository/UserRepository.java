package com.ekreative.hackathon.challenge.model.repository;

import com.ekreative.hackathon.challenge.model.entity.Challenge;
import com.ekreative.hackathon.challenge.model.entity.User;
import com.ekreative.hackathon.challenge.model.repository.exception.EntityNotFoundException;
import com.ekreative.hackathon.challenge.model.repository.rowmapper.UserRowMapper;
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

public class UserRepository implements BasicRepository<User> {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert userInsert;

    public UserRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.userInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    public Collection<User> findAllUsersWhoCompleteChallenge(int challengeId) {
        Map<String, Object> params = new HashMap<>();
        params.put("challenge_id", challengeId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        String sql = "SELECT\n" +
                "  u.id      AS user_id,\n" +
                "  u.uuid,\n" +
                "  u.first_name,\n" +
                "  u.last_name,\n" +
                "  u.enabled,\n" +
                "  u.created AS user_created\n" +
                "FROM users u\n" +
                "  RIGHT JOIN complete_challenge cc ON u.id = cc.user_id\n" +
                "WHERE cc.challenge_id=:challenge_id";

        try {
            return this.namedParameterJdbcTemplate.query(
                    sql,
                    sqlParameterSource,
                    new UserRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException();
        }
    }

    public User findByUuid(String UUID) {
        Map<String, Object> params = new HashMap<>();
        params.put("uuid", UUID);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        String sql = "SELECT u.id AS user_id, u.uuid, u.first_name, u.last_name, u.enabled, u.created  AS user_created " +
                "FROM users u " +
                "WHERE u.uuid=:uuid";

        try {
            return this.namedParameterJdbcTemplate.queryForObject(
                    sql,
                    sqlParameterSource,
                    new UserRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public User findById(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        String sql = "SELECT u.id AS user_id, u.uuid, u.first_name, u.last_name, u.enabled, u.created AS user_created " +
                "FROM users u " +
                "WHERE u.id=:id";

        try {
            return this.namedParameterJdbcTemplate.queryForObject(
                    sql,
                    sqlParameterSource,
                    new UserRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Collection<User> findAll() {
        String sql = "SELECT id AS user_id, uuid, first_name, last_name, enabled, created AS user_created " +
                "FROM users";

        try {
            return this.namedParameterJdbcTemplate.query(
                    sql,
                    new UserRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void remove(User user) {
        String sql = "DELETE FROM users WHERE id=:id";
        this.remove(sql, user.getId(), namedParameterJdbcTemplate);
    }

    public void saveCompleteChallenge(User user, Challenge challenge) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", user.getId());
        params.put("challenge_id", challenge.getId());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        String sql = "INSERT INTO complete_challenge (user_id, challenge_id) VALUES (:user_id, :challenge_id) ON CONFLICT DO NOTHING";

        this.namedParameterJdbcTemplate.update(
                sql,
                sqlParameterSource
        );
    }

    @Override
    public void save(User user) {
        if (user.isNew()) {
            add(user);
        } else {
            update(user);
        }
    }

    private void add(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("uuid", user.getUUID());
        params.put("first_name", user.getFirstName());
        params.put("last_name", user.getLastName());
        params.put("enabled", user.getEnabled());
        if (user.getCreated() != null) {
            params.put("created", LocalDateTimeUtils.toLong(user.getCreated()));
        } else {
            params.put("created", null);
        }

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        Number id = this.userInsert.executeAndReturnKey(sqlParameterSource);
        user.setId(id.intValue());
    }

    private void update(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", user.getId());
        params.put("uuid", user.getUUID());
        params.put("first_name", user.getFirstName());
        params.put("last_name", user.getLastName());
        params.put("enabled", user.getEnabled());
        if (user.getCreated() != null) {
            params.put("created", LocalDateTimeUtils.toLong(user.getCreated()));
        } else {
            params.put("created", null);
        }

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        String sql = "UPDATE users " +
                "SET uuid=:uuid, first_name=:first_name, last_name=:last_name, created=:created, enabled=:enabled " +
                "WHERE id=:id";

        this.namedParameterJdbcTemplate.update(
                sql,
                sqlParameterSource
        );
    }
}
