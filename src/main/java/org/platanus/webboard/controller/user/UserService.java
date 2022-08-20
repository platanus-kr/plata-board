package org.platanus.webboard.controller.user;

import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRole;

import java.util.List;

public interface UserService {
    User join(User user) throws Exception;

    User add(User user) throws Exception;

    User update(User user, User sessionUser) throws Exception;

    User update(User user) throws Exception;

    User updateRoleByUserId(long id, UserRole role);

    void revoke(User user) throws Exception;

    // todo: 사용자를 지울 때 작성 글과 코멘트 전부를 삭제되도록 할 것
    void delete(User user) throws Exception;

    User findById(long id) throws Exception;

    User findByUsername(String username) throws Exception;

    User findByUsername(Object principal) throws Exception;

    User findByNickname(String nickname) throws Exception;

    User findByEmail(String email) throws Exception;

    List<User> findByRole(UserRole role);

    List<User> findAll();
}
