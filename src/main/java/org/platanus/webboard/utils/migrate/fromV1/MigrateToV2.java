package org.platanus.webboard.utils.migrate.fromV1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/migrate")
@RequiredArgsConstructor
public class MigrateToV2 {

    private final UserService userService;
    private final RoleService roleService;
    private final MigrationMapperToV2 migrationMapperFromV1;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/toV2")
    @Transactional
    public ResponseEntity<?> migrateFromV1() throws Exception {
        log.info("Start Migrate from V1 to V2");
        log.info("Encrypt Password...");
        List<MigrationUser> allV1Users = migrationMapperFromV1.findAll();
        List<ErrorDto> errors = new ArrayList<>();
        for (MigrationUser v1User : allV1Users) {
            try {
                String v2Password = passwordEncoder.encode(v1User.getPassword());
                UserRole v2UserRole = UserRole.ROLE_USER;
                if (v1User.equals("ADMIN")) {
                    v2UserRole = UserRole.ROLE_ADMIN;
                }
                if (v1User.equals("USER")) {
                    v2UserRole = UserRole.ROLE_USER;
                }
                User v2User = MigrationUser.toV2User(v1User, v2Password, v2UserRole);
                log.info("Update password User : {} -> before : {} / after : {}",
                        v1User.getUsername(), v1User.getPassword(), v2User);
                userService.update(v2User);

                log.info("Update role User : {}", v1User.getUsername());
                Role role = Role.builder()
                        .role(v2UserRole)
                        .userId(v1User.getId())
                        .build();
                roleService.join(role);

            } catch (Exception e) {
                ErrorDto errorDto = ErrorDto.builder()
                        .errorId(9999L)
                        .errorCode("9999")
                        .errorMessage(v1User.getUsername() + " fail migration.")
                        .build();
                errors.add(errorDto);
            }
        }
        if (errors.size() > 0) {
            throw new Exception(errors.toString());
        }
        return ResponseEntity.ok("200");
    }
}
