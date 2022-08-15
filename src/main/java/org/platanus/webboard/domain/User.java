package org.platanus.webboard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
}
