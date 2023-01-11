package org.platanus.webboard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.platanus.webboard.domain.UserRepository;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserServiceTest {

    private UserRepository userRepository;

    @Test
    void join() {
    }

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