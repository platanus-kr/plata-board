package org.platanus.webboard.controller.login;

import org.platanus.webboard.domain.User;

public interface LoginService {
    User login(String username, String password) throws Exception;
}
