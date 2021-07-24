package org.platanus.webboard.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.platanus.webboard.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryTest {

    public static final DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:db/schema.sql")
            .build();

    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    UserRepository repository = new UserRepository(jdbcTemplate);
    User user;

    @BeforeEach
    public void beforeEach() {
        user = new User();
        user.setUsername("platanus");
        user.setEmail("platanus.kr@gmail.com");
        user.setPassword("aaa");
        user.setNickname("PLA");
    }

    @Test
    public void save() {
        repository.save(user);
        User result = repository.findById(user.getId()).get();
        assertEquals(user, result);
        repository.delete(user);
    }

    @Test
    public void findByUsername() {
        repository.save(user);
        User result = repository.findByUsername(user.getUsername()).get();
        assertEquals(user, result);
        repository.delete(user);
    }

    @Test
    public void findByNickname() {
        repository.save(user);
        User result = repository.findByNickname(user.getNickname()).get();
        assertEquals(user, result);
        repository.delete(user);
    }

    @Test
    public void findByEmail() {
        repository.save(user);
        User result = repository.findByEmail(user.getEmail()).get();
        assertEquals(user, result);
        repository.delete(user);
    }
}
