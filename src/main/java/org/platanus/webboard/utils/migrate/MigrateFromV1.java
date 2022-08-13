package org.platanus.webboard.utils.migrate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.permission.HasAdminRole;
import org.platanus.webboard.controller.board.dto.ErrorDto;
import org.platanus.webboard.controller.user.RoleService;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.Role;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController("/api/migrate")
@RequiredArgsConstructor
public class MigrateFromV1 {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/toV2")
    @HasAdminRole
    @Transactional
    public ResponseEntity<?> migrateFromV1() throws Exception {
        log.info("Start Migrate from V1 to V2");
        log.info("Encrypt Password...");
        List<User> allUsers = userService.findAll();
        List<ErrorDto> errors = new ArrayList<>();
        for (User user : allUsers) {
            try {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userService.update(user);
            } catch (Exception e) {
                ErrorDto errorDto = ErrorDto.builder()
                        .errorId(9999L)
                        .errorCode("9999")
                        .errorMessage(user.getUsername() + "비밀번호 암호화 실패")
                        .build();
                errors.add(errorDto);
            }
        }
        if (errors.size() > 0) {
            throw new Exception(errors.toString());
        }
        log.info("Add Roles..");
        for (User user : allUsers) {
            Role role = Role.builder()
                    .role(UserRole.ROLE_USER)
                    .userId(user.getId())
                    .build();
            roleService.join(role);
        }
        return ResponseEntity.ok("200");
    }
}
