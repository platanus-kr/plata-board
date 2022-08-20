package org.platanus.webboard.controller.board.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.property.PropertyEnvironment;
import org.platanus.webboard.config.security.permission.HasUserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class TestRestController {
    private final PropertyEnvironment propertyEnvironment;

    @Value("${plataboard.environment.profile}")
    private String profile;

    @GetMapping("/auth")
    @HasUserRole
    public ResponseEntity<?> doTest(@AuthenticationPrincipal Object principal) {
        log.info("authTest OK");
        log.info((String) principal);
        return ResponseEntity.ok("200");
    }

    @GetMapping("/env")
    public ResponseEntity<?> getEnd() {
        log.info(profile);
        log.info(propertyEnvironment.getProfile());
        return ResponseEntity.ok(propertyEnvironment.getProfile());
    }
}
