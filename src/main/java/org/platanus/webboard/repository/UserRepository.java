package org.platanus.webboard.repository;

import org.platanus.webboard.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("users").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", user.getUsername());
        parameters.put("password", user.getPassword());
        parameters.put("nickname", user.getNickname());
        parameters.put("email", user.getEmail());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setId(key.longValue());
        return user;
    }

    public int delete(User user) {
        return jdbcTemplate.update("delete from users where id = ?", user.getId());
    }

    public Optional<User> findById(long id) {
        List<User> result = jdbcTemplate
                .query("select * from users where id = ?", userRowMapper(), id);
        return result.stream().findAny();
    }

    public Optional<User> findByUsername(String username) {
        List<User> result = jdbcTemplate
                .query("select * from users where username = ?", userRowMapper(), username);
        return result.stream().findAny();
    }

    public Optional<User> findByEmail(String email) {
        List<User> result = jdbcTemplate
                .query("select * from users where email = ?", userRowMapper(), email);
        return result.stream().findAny();
    }

    public Optional<User> findByNickname(String nickname) {
        List<User> result = jdbcTemplate
                .query("select * from users where nickname = ?", userRowMapper(), nickname);
        return result.stream().findAny();
    }

    public List<User> findAll() {
        return jdbcTemplate.query("select * form users", userRowMapper());
    }


    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setNickname(rs.getString("nickname"));
            user.setEmail(rs.getString("email"));
            return user;
        };
    }

}
