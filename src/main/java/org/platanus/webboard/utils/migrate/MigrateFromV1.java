package org.platanus.webboard.utils.migrate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.user.RoleService;
import org.platanus.webboard.controller.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/api/migrate")
@PreAuthorize("hasRole(UserRole.ADMIN)")
@RequiredArgsConstructor
public class MigrateFromV1 {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/toV2")
    public ResponseEntity<?> migrateFromV1() {


        return ResponseEntity.ok("200");
    }
}
