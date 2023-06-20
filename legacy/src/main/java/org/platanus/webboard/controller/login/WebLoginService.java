package org.platanus.webboard.controller.login;

import org.platanus.webboard.domain.User;

public interface WebLoginService {
    User login(String username, String password) throws Exception;
}
