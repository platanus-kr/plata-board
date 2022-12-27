package org.platanus.webboard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.platanus.webboard.config.security.dto.UserClaimDto;
import org.platanus.webboard.controller.login.dto.UserSessionDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    @Email
    private String email;
    private boolean deleted;
    /**
     * 대표 롤
     */
    private UserRole role;

    /**
     * 사용자가 갖고 있는 모든 롤
     **/
//    private List<Role> roles;
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
