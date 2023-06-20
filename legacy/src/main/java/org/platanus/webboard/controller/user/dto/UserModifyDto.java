package org.platanus.webboard.controller.user.dto;

import lombok.Data;
import org.platanus.webboard.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserModifyDto {
    private long id;
    private String username;
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    @Email
    private String email;

    public static User from(UserModifyDto userModifyDto) {
        return User.builder()
                .id(userModifyDto.getId())
                .username(userModifyDto.getUsername())
                .password(userModifyDto.getPassword())
                .nickname(userModifyDto.getNickname())
                .email(userModifyDto.getEmail())
                .deleted(false)
                .build();
    }
}
