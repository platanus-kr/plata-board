package org.platanus.webboard.controller.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.constant.MessageConstant;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserService userService;

    @Override
    public User login(String username, String password) throws Exception {
        Optional<User> returnUser = Optional.ofNullable(userService.findByUsername(username));
        return returnUser
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> {
                    log.info(MessageConstant.LOGIN_FAILED_LOG, username);
                    throw new IllegalArgumentException(MessageConstant.LOGIN_FAILED);
                });
    }
}
