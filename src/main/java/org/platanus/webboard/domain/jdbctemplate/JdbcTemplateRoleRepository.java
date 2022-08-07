package org.platanus.webboard.domain.jdbctemplate;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Role;
import org.platanus.webboard.domain.RoleRepository;
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

@Repository
@RequiredArgsConstructor
public class JdbcTemplateRoleRepository implements RoleRepository {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @PostConstruct
    public void init() {
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("ROLES");
    }

    @Override
    public Role save(Role role) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ROLENAME", role.getRoleName());
        parameters.put("USER_ID", role.getUserId());
        jdbcInsert.execute(new MapSqlParameterSource(parameters));
        return role;
    }

    @Override
    public int delete(Role role) {
        return jdbcTemplate.update(QueryConstant.ROLE_DELETE, role.getRoleName(), role.getUserId());
    }

    public final static String ROLE_FIND_ALL = "select * from roles";

    @Override
    public List<Role> findAll() {
        return jdbcTemplate.query(ROLE_FIND_ALL, roleRowMapper());
    }

    private RowMapper<Role> roleRowMapper() {
        return (rs, rowNum) -> Role.builder()
                .roleName(UserRole.valueOf(rs.getString("rolename")))
                .userId(rs.getLong("user_id"))
                .build();

    }
}
