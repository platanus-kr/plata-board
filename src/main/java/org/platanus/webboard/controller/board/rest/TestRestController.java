package org.platanus.webboard.controller.board.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.property.PropertyEnvironment;
import org.platanus.webboard.config.security.dto.UserClaimDto;
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
@Api(tags = {"테스트 용도 RST API Controller"})
public class TestRestController {
    private final PropertyEnvironment propertyEnvironment;

    @Value("${plataboard.environment.profile}")
    private String profile;

    @GetMapping("/auth")
    @HasUserRole
    public ResponseEntity<?> doTest(@AuthenticationPrincipal UserClaimDto user) {
        log.info("authTest OK");
        log.info(user.toString());
        return ResponseEntity.ok("200");
    }

    @GetMapping("/env")
    public ResponseEntity<?> getEnd() {
        log.info(profile);
        log.info(propertyEnvironment.getProfile());
        return ResponseEntity.ok(propertyEnvironment.getProfile());
    }
}
