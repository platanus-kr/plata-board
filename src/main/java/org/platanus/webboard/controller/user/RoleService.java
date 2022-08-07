package org.platanus.webboard.controller.user;

import org.platanus.webboard.domain.Role;
import org.platanus.webboard.domain.User;

import java.util.List;

public interface RoleService {
    Role join(Role role);

    int remove(Role role);

    List<Role> findAll();

    List<Role> findByUser(User user);
}
