package org.platanus.webboard.web.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.fail;

public class UserServiceTest {
    public static final DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:db/schema.sql")
            .build();

    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    UserRepository repository = new UserRepository(jdbcTemplate);
    UserService service = new UserService(repository);
    User user;

    @BeforeEach
    public void beforeEach() {
        user = new User();
        user.setUsername("platanus");
        user.setEmail("platanus.kr@gmail.com");
        user.setPassword("aaa");
        user.setNickname("PLA");
        user.setDeleted(false);
    }

    @Test
    public void join() {
        try {
            service.join(user);
            service.revoke(user);
            service.delete(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void revoke() {
        try {
            service.join(user);
            service.revoke(user);
            User getUser = repository.findByUsername("platanus").get();
            if (!getUser.isDeleted())
                fail();
            service.delete(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void findRevokedUser() throws Exception {
        try {
            service.join(user);
            service.revoke(user);
            User getUser = service.findById(user.getId());
            fail();
            service.delete(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            service.delete(user);
        }
    }

    @Test
    public void findById() {
        try {
            service.join(user);
            User getUser = service.findById(user.getId());
            service.revoke(user);
            service.delete(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void findByUsername() {
        try {
            service.join(user);
            User getUser = service.findByUsername(user.getUsername());
            service.revoke(user);
            service.delete(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void findByNickname() {
        try {
            service.join(user);
            User getUser = service.findByNickname(user.getNickname());
            service.revoke(user);
            service.delete(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void findByEmail() {
        try {
            service.join(user);
            User getUser = service.findByEmail(user.getEmail());
            service.revoke(user);
            service.delete(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}
