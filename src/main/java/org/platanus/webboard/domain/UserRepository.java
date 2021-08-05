package org.platanus.webboard.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("users").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", user.getUsername());
        parameters.put("password", user.getPassword());
        parameters.put("nickname", user.getNickname());
        parameters.put("email", user.getEmail());
        parameters.put("deleted", user.isDeleted());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setId(key.longValue());
        return user;
    }

    public int delete(User user) {
        return jdbcTemplate.update("delete from USERS where ID = ?", user.getId());
    }

    public int update(User user) {
        return jdbcTemplate
                .update("update users set USERNAME =?, PASSWORD = ?, NICKNAME = ?, EMAIL = ? where ID = ?",
                        user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail(), user.getId());
    }

    public int updateDeleteFlag(User user) {
        return jdbcTemplate.update(
                "update USERS set DELETED = ? where ID = ?",
                user.isDeleted(), user.getId());
    }

    public Optional<User> findById(long id) {
        List<User> result = jdbcTemplate
                .query("select * from USERS where ID = ?", userRowMapper(), id);
        return result.stream().findAny();
    }

    public Optional<User> findByUsername(String username) {
        List<User> result = jdbcTemplate
                .query("select * from USERS where USERNAME = ?", userRowMapper(), username);
        return result.stream().findAny();
    }

    public Optional<User> findByEmail(String email) {
        List<User> result = jdbcTemplate
                .query("select * from USERS where EMAIL = ?", userRowMapper(), email);
        return result.stream().findAny();
    }

    public Optional<User> findByNickname(String nickname) {
        List<User> result = jdbcTemplate
                .query("select * from USERS where NICKNAME = ?", userRowMapper(), nickname);
        return result.stream().findAny();
    }

    public List<User> findAll() {
        return jdbcTemplate.query("select * from USERS", userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setNickname(rs.getString("nickname"));
            user.setEmail(rs.getString("email"));
            user.setDeleted(rs.getBoolean(("deleted")));
            return user;
        };
    }
}
