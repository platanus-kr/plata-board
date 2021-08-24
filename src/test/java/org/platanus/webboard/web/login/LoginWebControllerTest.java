package org.platanus.webboard.web.login;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.platanus.webboard.auth.utils.SessionConst;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.web.login.dto.UserLoginDto;
import org.platanus.webboard.web.login.dto.UserSessionDto;
import org.platanus.webboard.web.user.UserService;
import org.springframework.validation.BindingResult;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginWebControllerTest {

    @Test
//    @DisplayName("로그인 성공시 세션이 잘 기록되는지 확인")
    public void test_login_success_and_write_session() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("test11");
        UserSessionDto userSessionDto = new UserSessionDto();
        userSessionDto = UserSessionDto.from(user, null, null);

        UserService userService = Mockito.mock(UserService.class);
        Mockito.when(userService.findByUsername("test")).thenReturn(user);
        LoginService loginService = new LoginService(userService);

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername("test");
        userLoginDto.setPassword("test11");

        Map<String, Object> mockSessionMap = new HashMap<>();
        HttpSession httpSession = new MockHttpSession(mockSessionMap);
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(httpServletRequest.getSession(true)).thenReturn(httpSession);

        LoginWebController loginWebController = new LoginWebController(loginService);
        String result = loginWebController.login(userLoginDto, bindingResult, "/success", httpServletRequest);
        assertEquals("redirect:/success", result);
        assertEquals(userSessionDto, mockSessionMap.get(SessionConst.LOGIN_USER));
    }

    @Test
//    @DisplayName("로그인 실패시 세션에 기록이 안되고 로그인 폼으로 반환하는지 테스트")
    public void test_login_fail() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("test11");

        UserService userService = Mockito.mock(UserService.class);
        Mockito.when(userService.findByUsername("test")).thenReturn(user);
        LoginService loginService = new LoginService(userService);

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername("test");
        userLoginDto.setPassword("test__failed!!!");

        Map<String, Object> mockSessionMap = new HashMap<>();
        HttpSession httpSession = new MockHttpSession(mockSessionMap);
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(httpServletRequest.getSession(true)).thenReturn(httpSession);

        LoginWebController loginWebController = new LoginWebController(loginService);

        assertEquals("login/login_form", loginWebController.login(userLoginDto, bindingResult, "/success", httpServletRequest));
        assertEquals(null, mockSessionMap.get(SessionConst.LOGIN_USER));
    }

    public static class MockHttpSession implements HttpSession {
        private final Map<String, Object> mockSessionMap;

        public MockHttpSession(Map<String, Object> mockSessionMap) {
            this.mockSessionMap = mockSessionMap;
        }

        @Override
        public long getCreationTime() {
            return 0;
        }

        @Override
        public String getId() {
            return null;
        }

        @Override
        public long getLastAccessedTime() {
            return 0;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public void setMaxInactiveInterval(int interval) {

        }

        @Override
        public int getMaxInactiveInterval() {
            return 0;
        }

        @Override
        public HttpSessionContext getSessionContext() {
            return null;
        }

        @Override
        public Object getAttribute(String name) {
            return this.mockSessionMap.get(name);
        }

        @Override
        public Object getValue(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;
        }

        @Override
        public String[] getValueNames() {
            return new String[0];
        }

        @Override
        public void setAttribute(String name, Object value) {
            this.mockSessionMap.put(name, value);
        }

        @Override
        public void putValue(String name, Object value) {

        }

        @Override
        public void removeAttribute(String name) {

        }

        @Override
        public void removeValue(String name) {

        }

        @Override
        public void invalidate() {

        }

        @Override
        public boolean isNew() {
            return false;
        }
    }

}