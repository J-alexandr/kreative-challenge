package com.ekreative.hackathon.challenge.model.repository;

import com.ekreative.hackathon.challenge.model.entity.BasicEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface BasicRepository<T extends BasicEntity> {

    T findById(int id);

    Collection<T> findAll();

    void remove(T t);

    void save(T t);

    default void remove(String sql, Integer id, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);

        namedParameterJdbcTemplate.update(
                sql,
                sqlParameterSource
        );
    }
}
