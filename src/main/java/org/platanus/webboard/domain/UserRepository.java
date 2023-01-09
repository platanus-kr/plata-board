package org.platanus.webboard.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //User save(User user);
    //
    //int delete(User user);
    //
	// 없는 메소드
    //int update(User user);
    //
	// 이것도 없는 메소드. 그냥 save로 통일해야할듯.
    //int updateDeleteFlag(User user);
    //
    Optional<User> findById(long id);
    //
    Optional<User> findByUsername(String username);
    //
    Optional<User> findByEmail(String email);
    //
    //Optional<User> findByNickname(String nickname);
	Optional<User> findByNickname(String nickname);
    //
    List<User> findByRole(UserRole role);
    //
    List<User> findAll();
    //
	// 얘도 deleteAll로 바껴야됨..
    //void allDelete();

}
