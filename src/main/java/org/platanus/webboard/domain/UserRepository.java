package org.platanus.webboard.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    int delete(User user);

    int update(User user);

    int updateDeleteFlag(User user);

    Optional<User> findById(long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    List<User> findByRole(UserRole role);

    List<User> findAll();

    void allDelete();

}
