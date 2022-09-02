package org.platanus.webboard.config.security;

import lombok.Getter;
import org.platanus.webboard.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserAdapter extends org.springframework.security.core.userdetails.User {

    private final org.platanus.webboard.domain.User user;
    private final List<Role> roles;

    public UserAdapter(org.platanus.webboard.domain.User user, List<Role> roles) {
        super(user.getUsername(), user.getPassword(), authorities(roles));
        this.user = user;
        this.roles = roles;
    }

    public static UserAdapter from(org.platanus.webboard.domain.User user, List<Role> roles) {
        return new UserAdapter(user, roles);
    }

    private static Collection<? extends GrantedAuthority> authorities(List<Role> roles) {
        return roles.stream()
                .map(v -> new SimpleGrantedAuthority(v.getRole().getKey()))
                .collect(Collectors.toList());
    }
}
