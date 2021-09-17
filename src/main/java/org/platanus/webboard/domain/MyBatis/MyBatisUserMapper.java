package org.platanus.webboard.domain.MyBatis;

import org.apache.ibatis.annotations.Mapper;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRepository;
import org.platanus.webboard.domain.UserRole;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MyBatisUserMapper extends UserRepository {
    @Override
    User save(@Param("user") User user);

    @Override
    int delete(@Param("user") User user);

    @Override
    int update(@Param("user") User user);

    @Override
    int updateDeleteFlag(@Param("user") User user);

    @Override
    Optional<User> findById(@Param("id") long id);

    @Override
    Optional<User> findByUsername(@Param("username") String username);

    @Override
    Optional<User> findByEmail(@Param("email") String email);

    @Override
    Optional<User> findByNickname(@Param("nickname") String nickname);

    @Override
    List<User> findByRole(@Param("role") UserRole role);

    @Override
    List<User> findAll();

    @Override
    void allDelete();
}
