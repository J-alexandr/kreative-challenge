package com.ekreative.hackathon.challenge.model.repository.util;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcTemplateQueryExecutionUtils {

    private JdbcTemplateQueryExecutionUtils() {
    }

    public static Integer countRowsInTable(NamedParameterJdbcTemplate namedParameterJdbcTemplate, String sql) {
        return namedParameterJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource(),
                Integer.class
        );
    }

    public static Long getLong(NamedParameterJdbcTemplate namedParameterJdbcTemplate, String sql) {
        return namedParameterJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource(),
                Long.class
        );
    }
}
