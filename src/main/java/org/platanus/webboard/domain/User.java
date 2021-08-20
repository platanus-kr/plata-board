package org.platanus.webboard.domain;

import lombok.Data;
import org.platanus.webboard.web.login.dto.UserSessionDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class User {
    private long id;

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

    public static User fromLoginSessionDto(UserSessionDto userSessionDto) {
        User user = new User();
        user.setId(userSessionDto.getId());
        user.setUsername(userSessionDto.getUsername());
        user.setNickname(userSessionDto.getNickname());
        user.setEmail(userSessionDto.getEmail());
        return user;
    }
}
