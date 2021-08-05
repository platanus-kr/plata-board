package org.platanus.webboard.web.login.dto;

import lombok.Data;

@Data
public class LoginUserDto {
    private String username;
    private String nickname;
    private String password;
}
