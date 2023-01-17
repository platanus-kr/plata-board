package org.platanus.webboard.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByUserId(long userId);

    //delete from ROLES where ROLENAME = ? and USER_ID = ?
    @Deprecated
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from Role r where r.role = :#{#role} and r.userId = :userId")
    int delete(@Param("role") UserRole role, @Param("userId") Long userId);
}
