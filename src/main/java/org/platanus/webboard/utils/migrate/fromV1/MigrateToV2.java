package org.platanus.webboard.utils.migrate.fromV1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.constant.ConfigConstant;
import org.platanus.webboard.controller.board.dto.ErrorDto;
import org.platanus.webboard.controller.user.RoleService;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.Role;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRole;
import org.springframework.beans.factory.annotation.Value;
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
    private final MigrationFromV1Mapper migrationFromV1Mapper;
    private final PasswordEncoder passwordEncoder;
    @Value("${plataboard.environment.profile}")
    private String profile;

    /**
     * v1에서 v2로 마이그레이션 하는 컨트롤러. <br />
     * 프로덕션에서 실행시 소스 수정 필요 <br /><br />
     * <b>v2 변경사항</b> <br />
     * - 패스워드 암호화 (변경) <br />
     * - 사용자 권한 ROLE 추가 <br />
     *
     * @return 마이그레이션 결과
     * @throws Exception
     */
    @GetMapping("/toV2")
    @Transactional
    public ResponseEntity<?> migrateFromV1() throws Exception {
        // properties 파일 내 plataboard.environment.profile이 local 일 때만 실행됨.
        // 적절하게 환경에 따라 변경 필요.
        if (profile.equals(ConfigConstant.PROPERTY_ENV_PROFILE_PRODUCTION)) {
            return ResponseEntity.badRequest().body("로컬 환경이 아닙니다.");
        }
        log.info("Start Migrate from V1 to V2");
        List<MigrationUser> allV1Users = migrationFromV1Mapper.findAll();
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
                roleService.add(role);

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
