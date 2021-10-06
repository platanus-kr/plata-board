package org.platanus.webboard.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.platanus.webboard.domain.JdbcTemplate.JdbcTemplateUserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public class JdbcTemplateUserRepositoryTest {

    private static JdbcTemplateUserRepository jdbcTemplateUserRepository;
    private User user;

    @BeforeAll
    static void beforeAll() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:db/schema.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplateUserRepository = new JdbcTemplateUserRepository(jdbcTemplate);
        jdbcTemplateUserRepository.init();
    }

//    @AfterAll
//    static void afterAll() {
//        userRepository.allDelete();
//        List<User> findUsers = userRepository.findAll();
//        assertEquals(findUsers.size(), 0);
//    }

    @BeforeEach
    public void beforeEach() {
        user = new User();
    }

    @Test
    public void save() {
        user.setUsername("user01");
        user.setPassword("aaa");
        user.setNickname("user01");
        user.setEmail("user1@gmail.com");
        user.setDeleted(false);
        user.setRole(UserRole.USER);
        user = jdbcTemplateUserRepository.save(user);
        assertEquals(jdbcTemplateUserRepository.findById(user.getId()).get().getId(), user.getId());
    }

    @Test
    public void delete() {
        user.setUsername("user02");
        user.setPassword("aaa");
        user.setNickname("user02");
        user.setEmail("user2@gmail.com");
        user.setDeleted(false);
        user.setRole(UserRole.USER);
        user = jdbcTemplateUserRepository.save(user);
        int result = jdbcTemplateUserRepository.delete(user);
        assertEquals(result, 1);
    }

    @Test
    public void update() {
        user.setUsername("user03");
        user.setPassword("aaa");
        user.setNickname("user03");
        user.setEmail("user3@gmail.com");
        user.setDeleted(false);
        user.setRole(UserRole.USER);
        user = jdbcTemplateUserRepository.save(user);
        user.setUsername("userupdate03");
        int result = jdbcTemplateUserRepository.update(user);
        assertEquals(result, 1);
    }

    @Test
    public void updateDeleteFlag() {
        user.setUsername("user04");
        user.setPassword("aaa");
        user.setNickname("user04");
        user.setEmail("user4@gmail.com");
        user.setDeleted(false);
        user.setRole(UserRole.USER);
        user = jdbcTemplateUserRepository.save(user);
        user.setDeleted(true);
        int result = jdbcTemplateUserRepository.updateDeleteFlag(user);
        assertEquals(result, 1);
    }

    @Test
    public void findById() {
        user.setUsername("user05");
        user.setPassword("aaa");
        user.setNickname("user05");
        user.setEmail("user5@gmail.com");
        user.setDeleted(false);
        user.setRole(UserRole.USER);
        user = jdbcTemplateUserRepository.save(user);
        User findUser = jdbcTemplateUserRepository.findById(user.getId()).get();
        assertEquals(findUser.getId(), user.getId());
    }

    @Test
    public void findByUsername() {
        user.setUsername("user06");
        user.setPassword("aaa");
        user.setNickname("user06");
        user.setEmail("user6@gmail.com");
        user.setDeleted(false);
        user.setRole(UserRole.USER);
        user = jdbcTemplateUserRepository.save(user);
        User findUser = jdbcTemplateUserRepository.findByUsername(user.getUsername()).get();
        assertEquals(findUser.getUsername(), user.getUsername());
    }

    @Test
    public void findByEmail() {
        user.setUsername("user07");
        user.setPassword("aaa");
        user.setNickname("user07");
        user.setEmail("user7@gmail.com");
        user.setDeleted(false);
        user.setRole(UserRole.USER);
        user = jdbcTemplateUserRepository.save(user);
        User findUser = jdbcTemplateUserRepository.findByEmail(user.getEmail()).get();
        assertEquals(findUser.getEmail(), user.getEmail());
    }

    @Test
    public void findByNickname() {
        user.setUsername("user08");
        user.setPassword("aaa");
        user.setNickname("user08");
        user.setEmail("user8@gmail.com");
        user.setDeleted(false);
        user.setRole(UserRole.USER);
        user = jdbcTemplateUserRepository.save(user);
        User findUser = jdbcTemplateUserRepository.findByNickname(user.getNickname()).get();
        assertEquals(findUser.getNickname(), user.getNickname());
    }

    @Test
    public void findByRole() {
        user.setUsername("user091");
        user.setPassword("aaa");
        user.setNickname("user091");
        user.setEmail("user091@gmail.com");
        user.setDeleted(false);
        user.setRole(UserRole.USER);
        user = jdbcTemplateUserRepository.save(user);
        List<User> users = jdbcTemplateUserRepository.findByRole(UserRole.USER);
        User findUser = users.stream()
                .filter(u -> u.getUsername() == user.getUsername())
                .findAny()
                .get();
        System.out.println(findUser.getRole());
        assertEquals(findUser.getRole(), user.getRole());
    }

    @Test
    public void findAll() {
        user.setUsername("user09");
        user.setPassword("aaa");
        user.setNickname("user09");
        user.setEmail("user9@gmail.com");
        user.setDeleted(false);
        user.setRole(UserRole.USER);
        user = jdbcTemplateUserRepository.save(user);
        List<User> users = jdbcTemplateUserRepository.findAll();
        User findUser = users.stream()
                .filter(u -> u.getUsername() == user.getUsername())
                .findAny()
                .get();
        assertEquals(findUser.getUsername(), user.getUsername());
    }
}
