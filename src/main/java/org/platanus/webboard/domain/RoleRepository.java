package org.platanus.webboard.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // 기본 구현
    //Role save(Role save);
    // 기본 구현
    //int delete(UserRole userRole, long userId);

    List<Role> findAll();

    List<Role> findByUserId(long userId);
}
