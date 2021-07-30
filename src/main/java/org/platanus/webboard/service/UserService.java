package org.platanus.webboard.service;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;

    public User join(User user) throws Exception {
        if (userRepository.findByNickname(user.getNickname()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임 입니다.");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }
        if (userRepository.findByEmail((user.getEmail())).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }
        MessageDigest md = MessageDigest.getInstance(("SHA-256"));
        md.update(user.getPassword().getBytes());
        user.setPassword(String.format("%064x", new BigInteger(1, md.digest())));
        return userRepository.save(user);
    }

    public void revoke(User user) throws Exception {
        if (userRepository.findById(user.getId()).get().isDeleted())
            throw new IllegalArgumentException("이미 탈퇴한 회원입니다");
        user.setDeleted(true);
        userRepository.updateDeleteFlag(user);
    }

    // todo: 사용자를 지울 때 작성 글과 코멘트 전부를 삭제되도록 할 것
    public void delete(User user) throws Exception {
        if (!user.isDeleted())
            throw new IllegalArgumentException("탈퇴되지 않은 회원 입니다.");
        userRepository.delete(user);
    }

    public User findById(long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty() || user.get().isDeleted())
            throw new IllegalArgumentException("없는 회원 입니다.");
        return user.get();
    }

    public User findByUsername(String username) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty() || user.get().isDeleted())
            throw new IllegalArgumentException("없는 회원 입니다.");
        return user.get();
    }

    public User findByNickname(String nickname) throws Exception {
        Optional<User> user = userRepository.findByNickname(nickname);
        if (user.isEmpty() || user.get().isDeleted())
            throw new IllegalArgumentException("없는 회원 입니다.");
        return user.get();
    }

    public User findByEmail(String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty() || user.get().isDeleted())
            throw new IllegalArgumentException("없는 회원 입니다.");
        return user.get();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
