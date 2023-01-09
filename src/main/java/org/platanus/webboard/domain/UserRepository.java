package org.platanus.webboard.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /* 변경감지를 통해 JPA가 영속성을 관리 할 수 있도록 제거 할 것. 일단은 코드가 돌아야 하니.. */
    @Deprecated
    @Modifying(clearAutomatically = true)
    @Query(value = "update User u set u.username = :username, u.password = :password, u.nickname = :nickname, u.email = :email, u.role = :role where u.id = :id")
    int update(User user);

    /* 변경감지를 통해 JPA가 영속성을 관리 할 수 있도록 제거 할 것. 일단은 코드가 돌아야 하니.. */
    @Deprecated
    @Modifying(clearAutomatically = true)
    @Query(value = "update User u set u.deleted = :deleted where u.id = :id")
    int updateDeleteFlag(User user);

    Optional<User> findById(long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    List<User> findByRole(UserRole role);
}
