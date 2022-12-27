package org.platanus.webboard.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.constant.MessageConstant;
import org.platanus.webboard.controller.user.RoleService;
import org.platanus.webboard.domain.Role;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * UserDetailsService 구현 <br />
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            log.error(MessageConstant.UDS_USER_NOT_FOUND);
            throw new UsernameNotFoundException(MessageConstant.UDS_USER_NOT_FOUND);
        }
        User user = optUser.get();
        log.info(MessageConstant.UDS_USER_LOGIN, user.getUsername());
        List<Role> roles = roleService.findByUser(user);
//        Collection<SimpleGrantedAuthority> authorites = byUser.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getRole().getKey()))
//                .collect(Collectors.toList());
//        log.info("{} 의 권한 : {}", user.getUsername(), authorites.toString());
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorites);
        return UserAdapter.from(user, roles);
//        return new UserDetailsServiceImpl(user, roles);
    }
}
