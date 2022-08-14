package org.platanus.webboard.domain;

import java.util.List;

public interface RoleRepository {
    Role save(Role save);

    int delete(UserRole userRole, long userId);

    List<Role> findAll();

    List<Role> findByUserId(long userId);
}
