package org.platanus.webboard.web.user.dto;

import lombok.Data;
import org.platanus.webboard.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserModifyDto {
    private long id;

    @NotBlank
    private String username;
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    @Email
    private String email;

    public static User from(UserModifyDto userModifyDto) {
        User user = new User();
        user.setId(userModifyDto.getId());
        user.setUsername(userModifyDto.getUsername());
        user.setPassword(userModifyDto.getPassword());
        user.setNickname(userModifyDto.getNickname());
        user.setEmail(userModifyDto.getEmail());
        user.setDeleted(false);
        return user;
    }
}
