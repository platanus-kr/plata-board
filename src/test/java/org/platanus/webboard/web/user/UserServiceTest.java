package org.platanus.webboard.web.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Transactional
public class UserServiceTest {

    private static UserRepository userRepository;
    private static UserService userService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:db/schema.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        userRepository = new UserRepository(jdbcTemplate);
        userRepository.init();
        userService = new UserService(userRepository);
    }

//    @AfterAll
//    static void afterAll() {
//        userRepository.allDelete();
//    }

    @BeforeEach
    public void beforeEach() {
        user = new User();
    }

    @Test
    public void join() {
        try {
            user.setUsername("user11");
            user.setPassword("aaa");
            user.setNickname("user11");
            user.setEmail("user11@gmail.com");
            user.setDeleted(false);
            user = userService.join(user);
            assertEquals(userService.findById(user.getId()).getId(), user.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

//    @Test
//    public void join_duplicate_username() {
//
//    }
//
//    @Test
//    public void join_duplicate_nickname() {
//
//    }
//
//    @Test
//    public void join_duplicate_email() {
//
//    }

    @Test
    public void update() {
        try {
            user.setUsername("user12");
            user.setPassword("aaa");
            user.setNickname("user12");
            user.setEmail("user12@gmail.com");
            user.setDeleted(false);
            user = userService.join(user);
            user.setUsername("userupdate02");
            user = userService.update(user, user);
            assertEquals(userService.findById(user.getId()).getId(), user.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void revoke() {
        try {
            user.setUsername("user13");
            user.setPassword("aaa");
            user.setNickname("user13");
            user.setEmail("user13@gmail.com");
            user.setDeleted(false);
            user = userService.join(user);
            userService.revoke(user);
            userService.findById(user.getId());
            fail();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

//    @Test
//    public void revoke_already() {
//
//    }

    @Test
    public void findById() {
        try {
            user.setUsername("user14");
            user.setPassword("aaa");
            user.setNickname("user14");
            user.setEmail("user14@gmail.com");
            user.setDeleted(false);
            user = userService.join(user);
            assertEquals(userService.findById(user.getId()).getId(), user.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

//    @Test
//    public void findById_not_found() {
//
//    }

    @Test
    public void findByUsername() {
        try {
            user.setUsername("user15");
            user.setPassword("aaa");
            user.setNickname("user15");
            user.setEmail("user15@gmail.com");
            user.setDeleted(false);
            user = userService.join(user);
            assertEquals(userService.findByUsername(user.getUsername()).getUsername(), user.getUsername());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

//    @Test
//    public void findByUsername_not_found() {
//
//    }

    @Test
    public void findByNickname() {
        try {
            user.setUsername("user16");
            user.setPassword("aaa");
            user.setNickname("user16");
            user.setEmail("user16@gmail.com");
            user.setDeleted(false);
            user = userService.join(user);
            assertEquals(userService.findByNickname(user.getNickname()).getNickname(), user.getNickname());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

//    @Test
//    public void findByNickname_not_found() {
//
//    }

    @Test
    public void findByEmail() {
        try {
            user.setUsername("user17");
            user.setPassword("aaa");
            user.setNickname("user17");
            user.setEmail("user17@gmail.com");
            user.setDeleted(false);
            user = userService.join(user);
            assertEquals(userService.findByEmail(user.getEmail()).getEmail(), user.getEmail());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

//    @Test
//    public void findByEmail_not_found() {
//
//    }

    @Test
    public void findAll() {
        try {
            user.setUsername("user18");
            user.setPassword("aaa");
            user.setNickname("user18");
            user.setEmail("user18@gmail.com");
            user.setDeleted(false);
            user = userService.join(user);
            List<User> users = userService.findAll();
            User findUser = users.stream()
                    .filter(u -> u.getId() == user.getId())
                    .findAny()
                    .get();
            assertEquals(findUser.getId(), user.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}
