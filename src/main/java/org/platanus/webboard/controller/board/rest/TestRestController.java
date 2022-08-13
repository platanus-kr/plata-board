package org.platanus.webboard.controller.board.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.permission.HasUserRole;
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


    @GetMapping(value = "/auth")
    @HasUserRole
    public ResponseEntity<?> doTest(@AuthenticationPrincipal Object principal) {
        log.info("authTest OK");
        log.info((String) principal);
        return ResponseEntity.ok("200");
    }
}
