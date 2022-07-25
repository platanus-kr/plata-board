package org.platanus.webboard.controller.login.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginDto {
    @NotBlank
    private String username;
    private String nickname;

    @NotBlank
    private String password;
}
