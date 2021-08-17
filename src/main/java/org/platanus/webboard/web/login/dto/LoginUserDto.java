package org.platanus.webboard.web.login.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUserDto {
    @NotBlank
    private String username;
    private String nickname;

    @NotBlank
    private String password;
}
