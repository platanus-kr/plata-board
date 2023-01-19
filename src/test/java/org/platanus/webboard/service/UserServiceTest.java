package org.platanus.webboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.controller.user.UserServiceImpl;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRepository;
import org.platanus.webboard.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void add() {

    }

    @Test
    void update() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void updateRoleByUserId() {
    }

    @Test
    void revoke() {
    }

    @Test
    void delete() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByUsername() {
    }

    @Test
    void testFindByUsername() {
    }

    @Test
    void findByNickname() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByRole() {
    }

    @Test
    void findAll() {
    }
}