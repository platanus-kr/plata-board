package org.platanus.webboard.domain.jdbctemplate;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRepository;
import org.platanus.webboard.domain.UserRole;
import org.platanus.webboard.domain.utils.QueryConstant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @PostConstruct
    public void init() {
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("USERS").usingGeneratedKeyColumns("id");
    }

    public User save(User user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", user.getUsername());
        parameters.put("password", user.getPassword());
        parameters.put("nickname", user.getNickname());
        parameters.put("email", user.getEmail());
        parameters.put("deleted", user.isDeleted());
        parameters.put("role", user.getRole().getKey());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setId(key.longValue());
        return user;
    }

    public int delete(User user) {
        return jdbcTemplate.update(QueryConstant.USER_DELETE, user.getId());
    }

    public int update(User user) {
        return jdbcTemplate.update(QueryConstant.USER_UPDATE,
                user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail(), user.getRole().getKey(), user.getId());
    }

    public int updateDeleteFlag(User user) {
        return jdbcTemplate.update(QueryConstant.USER_UPDATE_DELETE_FLAG, user.isDeleted(), user.getId());
    }

    public Optional<User> findById(long id) {
        List<User> result = jdbcTemplate.query(QueryConstant.USER_FIND_BY_ID, userRowMapper(), id);
        return result.stream().findAny();
    }

    public Optional<User> findByUsername(String username) {
        List<User> result = jdbcTemplate.query(QueryConstant.USER_FIND_BY_USERNAME, userRowMapper(), username);
        return result.stream().findAny();
    }

    public Optional<User> findByEmail(String email) {
        List<User> result = jdbcTemplate.query(QueryConstant.USER_FIND_BY_EMAIL, userRowMapper(), email);
        return result.stream().findAny();
    }

    public Optional<User> findByNickname(String nickname) {
        List<User> result = jdbcTemplate.query(QueryConstant.USER_FIND_BY_NICKNAME, userRowMapper(), nickname);
        return result.stream().findAny();
    }

    public List<User> findByRole(UserRole role) {
        return jdbcTemplate.query(QueryConstant.USER_FIND_BY_ROLE, userRowMapper(), role.getKey());
    }

    public List<User> findAll() {
        return jdbcTemplate.query(QueryConstant.USER_FIND_ALL, userRowMapper());
    }

    public void allDelete() {
        jdbcTemplate.update(QueryConstant.USER_ALL_DELETE);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> User.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .nickname(rs.getString("nickname"))
                .email(rs.getString("email"))
                .deleted(rs.getBoolean("deleted"))
                .role(UserRole.valueOf(rs.getString("role")))
                .build();

    }
}
