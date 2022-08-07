package org.platanus.webboard.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRepository;
import org.platanus.webboard.domain.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User join(User user) throws Exception {
        if (userRepository.findByNickname(user.getNickname()).isPresent()) {
            log.info("User join #{}: 이미 존재하는 닉네임 입니다. - {}", user.getUsername(), user.getNickname());
            throw new IllegalArgumentException("이미 존재하는 닉네임 입니다.");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            log.info("User join #{}: 이미 존재하는 아이디 입니다.", user.getUsername());
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }
        if (userRepository.findByEmail((user.getEmail())).isPresent()) {
            log.info("User join #{}: 이미 존재하는 이메일 입니다. - {}", user.getUsername(), user.getEmail());
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }
//        MessageDigest md = MessageDigest.getInstance(("SHA-256"));
//        md.update(user.getPassword().getBytes());
//        user.setPassword(String.format("%064x", new BigInteger(1, md.digest())));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDeleted(false);
//        user.setRole(UserRole.USER);
        user = userRepository.save(user);
        log.info("User join #{}, {}", user.getId(), user.getUsername());
        return user;
    }

    @Override
    public User update(User user, User sessionUser) throws Exception {
        if (userRepository.findByNickname(user.getNickname()).isPresent() && !user.getNickname().equals(sessionUser.getNickname())) {
            log.info("User join #{}: 이미 존재하는 닉네임 입니다. - {}", user.getUsername(), user.getNickname());
            throw new IllegalArgumentException("이미 존재하는 닉네임 입니다.");
        }
        if (userRepository.findByEmail((user.getEmail())).isPresent() && !user.getEmail().equals(sessionUser.getEmail())) {
            log.info("User join #{}: 이미 존재하는 이메일 입니다. - {}", user.getUsername(), user.getEmail());
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }
        if (userRepository.update(user) == 1)
            return userRepository.findById(user.getId()).get();
        else {
            log.info("User update #{}: Repository Error.", user.getId());
            throw new IllegalArgumentException("정보 변경에 문제가 생겼습니다.");
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
            log.info("User revoke #{}: 이미 탈퇴한 회원 입니다.", user.getId());
            throw new IllegalArgumentException("이미 탈퇴한 회원입니다");
        }
        user.setDeleted(true);
        if (userRepository.updateDeleteFlag(user) != 1) {
            log.info("User revoke #{}: Repository Error.", user.getId());
            throw new IllegalArgumentException("정보 변경에 문제가 생겼습니다.");
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            log.error("사용자를 찾을 수 없음");
            throw new UsernameNotFoundException("사용자를 찾을 수 없음");
        }
        User user = optUser.get();
        log.info("사용자 로그인 : {}", user.getUsername());
        Collection<SimpleGrantedAuthority> authorites = roleService.findByUser(user).stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getKey()))
                .collect(Collectors.toList());
        log.info("{} 의 권한 : {}", user.getUsername(), authorites.toString());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorites);
    }
}
