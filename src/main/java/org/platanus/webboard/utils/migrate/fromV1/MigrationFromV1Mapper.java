package org.platanus.webboard.utils.migrate.fromV1;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.jdbctemplate.constant.QueryConstant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MigrationFromV1Mapper {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @PostConstruct
    public void init() {
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("USERS").usingGeneratedKeyColumns("id");
    }

    public List<MigrationUser> findAll() {
        return jdbcTemplate.query(QueryConstant.USER_FIND_ALL, userRowMapper());
    }

    private RowMapper<MigrationUser> userRowMapper() {
        return (rs, rowNum) -> {
            MigrationUser user = new MigrationUser();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setNickname(rs.getString("nickname"));
            user.setEmail(rs.getString("email"));
            user.setDeleted(rs.getBoolean(("deleted")));
            user.setV1UserRole(rs.getString("role"));
            return user;
        };

    }
}
