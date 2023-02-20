package org.platanus.webboard.controller.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.constant.MessageConstant;
import org.platanus.webboard.controller.board.exception.BoardException;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebLoginServiceImpl implements WebLoginService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User login(String username, String password) throws Exception {
        User findUser = userService.findByUsername(username);
        if (findUser != null && passwordEncoder.matches(password, findUser.getPassword())) {
            return findUser;
        }
        throw new IllegalArgumentException(MessageConstant.LOGIN_FAILED);
    }
}
