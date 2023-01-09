package org.platanus.webboard.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.platanus.webboard.config.constant.MessageConstant;
import org.platanus.webboard.domain.Role;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRepository;
import org.platanus.webboard.domain.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User join(User user) throws Exception {
        User addedUser;
        try {
            // 회원 추가
            addedUser = add(user);
            // 회원 역할 추가
            roleService.add(new Role(null, UserRole.ROLE_USER, user.getId()));
            // 관리자의 경우 관리자 역할 추가
            if (user.getRole().equals(UserRole.ROLE_ADMIN)) {
                roleService.add(new Role(null, UserRole.ROLE_ADMIN, user.getId()));
            }
        } catch (Exception e) {
            throw new RuntimeException(MessageConstant.USER_JOIN_FAILED);
        }
        return addedUser;
    }

    @Override
    public User add(User user) throws Exception {
        if (userRepository.findByNickname(user.getNickname()).isPresent()) {
            log.info(MessageConstant.USER_ALREADY_USE_NICKNAME_LOG, user.getUsername(), user.getNickname());
            throw new IllegalArgumentException(MessageConstant.USER_ALREADY_USE_NICKNAME);
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            log.info(MessageConstant.USER_ALREADY_USE_USERNAME_LOG, user.getUsername());
            throw new IllegalArgumentException(MessageConstant.USER_ALREADY_USE_USERNAME);
        }
        if (userRepository.findByEmail((user.getEmail())).isPresent()) {
            log.info(MessageConstant.USER_ALREADY_USE_EMAIL_LOG, user.getUsername(), user.getEmail());
            throw new IllegalArgumentException(MessageConstant.USER_ALREADY_USE_EMAIL);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDeleted(false);
        user = userRepository.save(user);
        log.info(MessageConstant.USER_JOIN_SUCCESS_LOG, user.getId(), user.getUsername());
        return user;
    }

    @Override
    public User update(User user, User sessionUser) throws Exception {
        if (userRepository.findByNickname(user.getNickname()).isPresent() && !user.getNickname().equals(sessionUser.getNickname())) {
            log.info(MessageConstant.USER_ALREADY_USE_NICKNAME_LOG, user.getUsername(), user.getNickname());
            throw new IllegalArgumentException(MessageConstant.USER_ALREADY_USE_NICKNAME);
        }
        if (userRepository.findByEmail((user.getEmail())).isPresent() && !user.getEmail().equals(sessionUser.getEmail())) {
            log.info(MessageConstant.USER_ALREADY_USE_EMAIL_LOG, user.getUsername(), user.getEmail());
            throw new IllegalArgumentException(MessageConstant.USER_ALREADY_USE_EMAIL);
        }
        if (userRepository.update(user) == 1)
            return userRepository.findById(user.getId()).get();
        else {
            log.info(MessageConstant.USER_UPDATE_FAILED_LOG, user.getId());
            throw new IllegalArgumentException(MessageConstant.USER_UPDATE_FAILED);
        }
    }

    @Override
    public User update(User user) throws Exception {
        if (userRepository.update(user) == 1)
            return userRepository.findById(user.getId()).get();
        else {
            log.info(MessageConstant.USER_UPDATE_FAILED_LOG, user.getId());
            throw new IllegalArgumentException(MessageConstant.USER_UPDATE_FAILED);
        }
    }

    @Override
    public User updateRoleByUserId(long id, UserRole role) {
        User user = userRepository.findById(id).get();
        if (role == UserRole.ROLE_USER)
            user.setRole(UserRole.ROLE_USER);
        else if (role == UserRole.ROLE_ADMIN)
            user.setRole(UserRole.ROLE_ADMIN);
        userRepository.update(user);
        return user;
    }

    @Override
    public void revoke(User user) throws Exception {
        if (userRepository.findById(user.getId()).get().isDeleted()) {
            log.info(MessageConstant.USER_ALREADY_REVOKE_LOG, user.getId());
            throw new IllegalArgumentException(MessageConstant.USER_ALREADY_REVOKE);
        }
        user.setDeleted(true);
        if (userRepository.updateDeleteFlag(user) != 1) {
            log.info(MessageConstant.USER_REVOKE_FAILED_LOG, user.getId());
            throw new IllegalArgumentException(MessageConstant.USER_REVOKE_FAILED);
        }
        log.info("User revoke #{}", user.getId());
    }

    @Override
    // todo: 사용자를 지울 때 작성 글과 코멘트 전부를 삭제되도록 할 것
    public void delete(User user) throws Exception {
        if (!user.isDeleted()) {
            log.info("User delete #{}: 탈퇴되지 않은 회원입니다.", user.getId());
            throw new IllegalArgumentException("탈퇴되지 않은 회원 입니다.");
        }
        userRepository.delete(user);
    }

    @Override
    public User findById(long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty() || user.get().isDeleted()) {
            log.info("User findById #{}: 없는 회원 입니다.", id);
            throw new IllegalArgumentException("없는 회원 입니다.");
        }
        return user.get();
    }

    @Override
    public User findByUsername(String username) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty() || user.get().isDeleted()) {
            log.info("User findByUsername - {}: 없는 회원 입니다.", username);
            throw new IllegalArgumentException("없는 회원 입니다.");
        }
        return user.get();
    }

    @Override
    public User findByUsername(Object principal) throws Exception {
        org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) principal;
        log.info("Get principal : " + userDetails.getUsername());
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        if (user.isEmpty() || user.get().isDeleted()) {
            log.info("User findByUsername - {}: 없는 회원 입니다.", principal);
            throw new IllegalArgumentException("없는 회원 입니다.");
        }
        return user.get();
    }

    @Override
    public User findByNickname(String nickname) throws Exception {
        Optional<User> user = userRepository.findByNickname(nickname);
        if (user.isEmpty() || user.get().isDeleted()) {
            log.info("User findByNickname - {}: 없는 회원 입니다.", nickname);
            throw new IllegalArgumentException("없는 회원 입니다.");
        }
        return user.get();
    }

    @Override
    public User findByEmail(String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty() || user.get().isDeleted()) {
            log.info("User findByEmail - {}: 없는 회원 입니다.", email);
            throw new IllegalArgumentException("없는 회원 입니다.");
        }
        return user.get();
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
