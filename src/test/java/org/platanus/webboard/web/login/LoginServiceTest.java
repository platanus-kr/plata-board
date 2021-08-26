package org.platanus.webboard.web.login;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRole;
import org.platanus.webboard.web.user.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginServiceTest {

    @Test
    public void test_login_success() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("test11");
        user.setRole(UserRole.USER);

        UserService userService = Mockito.mock(UserService.class);
        Mockito.when(userService.findByUsername("test")).thenReturn(user);

        LoginService loginService = new LoginService(userService);
        assertEquals(user, loginService.login("test", "test11"));
    }

    @Test
    public void test_login_fail() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("test22");
        user.setRole(UserRole.USER);

        UserService userService = Mockito.mock(UserService.class);
        Mockito.when(userService.findByUsername("test")).thenReturn(user);

        LoginService loginService = new LoginService(userService);
        assertThrows(IllegalArgumentException.class, () -> loginService.login("test", "test11"));
    }

}
