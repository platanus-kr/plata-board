package org.platanus.webboard.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.platanus.webboard.domain.User;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryTest {

    public static final DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:db/schema.sql")
            .build();
    UserRepository repository;
    User user;


    @BeforeEach
    public void beforeEach() {
        repository = new UserRepository(dataSource);

        user = new User();
        user.setUsername("platanus");
        user.setEmail("platanus.kr@gmail.com");
        user.setPassword("aaa");
        user.setNickname("PLA");
        repository.save(user);
    }

    @AfterEach
    public void afterEach() {
        repository.delete(user);
    }

    @Test
    public void save() {
        User result = repository.findById(user.getId()).get();
        assertEquals(user, result);
    }

    @Test
    public void findByUsername() {
        User result = repository.findByUsername(user.getUsername()).get();
        assertEquals(user, result);
    }

    @Test
    public void findByNickname() {
        User result = repository.findByNickname(user.getNickname()).get();
        assertEquals(user, result);
    }

    @Test
    public void findByEmail() {
        User result = repository.findByEmail(user.getEmail()).get();
        assertEquals(user, result);
    }
}
