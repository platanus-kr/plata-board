package org.platanus.webboard.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.platanus.webboard.SpringIntegratedTest;
import org.platanus.webboard.controller.user.RoleService;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.controller.user.UserServiceImpl;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRepository;
import org.platanus.webboard.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
class UserServiceTest {
    
    // 아 이게 왜 되는거지.. JDBC 쓸 때는 안됐는데..
    // 그때는 생성자 주입을 스프링에서 안해서 그런건가...
    @Autowired
    UserService userService;
    
    // 이런데 쓰라고 있는게 아닌듯함. 일단 User는 다른 서비스를 상속하지 않으니 mock 없이 가능한것.
    //@Mock
    //UserRepository userRepository;
    //
    //@Mock
    //RoleService roleService;
    //
    //@Mock
    //PasswordEncoder passwordEncoder;
    
    @BeforeEach
    void setup() throws Exception {
        User user = User.builder()
                .username("USER_SERVICE_TEST_USERNAME")
                .email("USERSERVICETESTEMAIL@PLATABOARD.ORG")
                .password("USER_SERVICE_TEST_PASSWORD")
                .nickname("USER_SERVICE_TEST_NICKNAME").build();
        userService.add(user);
    }

    @Test
    void add() throws Exception {
        //given
        User user = User.builder()
                .username("TEST1")
                .email("pla@platanus.me")
                .password("TEST1")
                .nickname("TEST1").build();
        //Mockito.when(userRepository.save(user))
        //        .thenReturn(User.builder()
        //                .id(1L)
        //                .username(user.getUsername())
        //                .email(user.getEmail())
        //                .password(user.getPassword())
        //                .nickname(user.getNickname())
        //                .build());
        
        //when
        User addedUser = userService.add(user);
        
        //then
        Assertions.assertThat(user.getUsername()).isEqualTo(addedUser.getUsername());
    }

    @Test
    void update_for_web() {
    }

    @Test
    void update_user() {
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