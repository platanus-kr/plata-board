package org.platanus.webboard.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /* 변경감지를 통해 JPA가 영속성을 관리 할 수 있도록 제거 할 것. 일단은 코드가 돌아야 하니.. */
    @Deprecated
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update User u set u.username = :#{#user.username}, u.password = :#{#user.password}, u.nickname = :#{#user.nickname}, u.email = :#{#user.email}, u.role = :#{#user.role} where u.id = :#{#user.id}")
    int update(@Param("user") User user);

    /* 변경감지를 통해 JPA가 영속성을 관리 할 수 있도록 제거 할 것. 일단은 코드가 돌아야 하니.. */
    @Deprecated
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update User u set u.deleted = :#{#user.deleted} where u.id = :#{#user.id}")
    int updateDeleteFlag(@Param("user") User user);

    Optional<User> findById(long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    List<User> findByRole(UserRole role);
}
