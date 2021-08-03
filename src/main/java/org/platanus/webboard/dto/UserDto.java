package org.platanus.webboard.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String nickname;
    private String password;
}
