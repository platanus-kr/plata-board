package org.platanus.webboard.domain;

import java.util.List;

public interface RoleRepository {
    Role save(Role save);

    int delete(Role role);

    List<Role> findAll();
}
