package org.platanus.webboard.domain;

import lombok.*;
import org.platanus.webboard.config.security.dto.UserClaimDto;
import org.platanus.webboard.controller.login.dto.UserSessionDto;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /* 회원 번호 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 회원 아이디 */
    @NotBlank
    private String username;

    /* 회원 비밀번호 */
    @NotBlank
    private String password;

    /* 회원 닉네임 */
    @NotBlank
    private String nickname;

    /* 회원 이메일 */
    @NotBlank
    @Email
    private String email;

    /* 회원 탈퇴여부 */
    private boolean deleted;

    /* 회원 롤 */
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public static User fromLoginSessionDto(UserSessionDto userSessionDto) {
        return User.builder()
                .id(userSessionDto.getId())
                .username(userSessionDto.getUsername())
                .nickname(userSessionDto.getNickname())
                .email(userSessionDto.getEmail())
                .role(userSessionDto.getRole())
                .build();
    }

    public static User fromUserClaimDto(UserClaimDto userClaimDto) {
        return User.builder().id(userClaimDto.getId())
                .username(userClaimDto.getUsername())
                .role(UserRole.valueOf(userClaimDto.getRoleKeys().get(0)))
                .build();
    }
}
