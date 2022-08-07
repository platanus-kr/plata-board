package org.platanus.webboard.controller.user;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.Role;
import org.platanus.webboard.domain.RoleRepository;
import org.platanus.webboard.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role join(Role role) {
        return roleRepository.save(role);
    }

    public int remove(Role role) {
        return roleRepository.delete(role.getRole(), role.getUserId());
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public List<Role> findByUser(User user) {
        return roleRepository.findByUserId(user.getId());
    }
}
