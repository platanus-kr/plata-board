package org.platanus.webboard.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            log.error("loadUserByUsername : 사용자를 찾을 수 없음");
            throw new UsernameNotFoundException("loadUserByUsername : 사용자를 찾을 수 없음");
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
