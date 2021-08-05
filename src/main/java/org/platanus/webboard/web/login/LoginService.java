package org.platanus.webboard.web.login;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.web.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserService userService;

    public User login(String username, String password) throws Exception {
        Optional<User> returnUser = Optional.ofNullable(userService.findByUsername(username));
        return returnUser
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new IllegalArgumentException("잘못된 로그인 정보 입니다."));


    }
}
