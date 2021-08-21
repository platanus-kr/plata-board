package org.platanus.webboard.web.login.dto;

import lombok.Data;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRole;

@Data
public class UserSessionDto {
    private long id;
    private String username;
    private String nickname;
    private String email;
    private String userAgent;
    private String userIp;
    private UserRole role;

    public static UserSessionDto from(User user, String userAgent, String userIp) {
        UserSessionDto loginSessionDto = new UserSessionDto();
        loginSessionDto.setId(user.getId());
        loginSessionDto.setUsername(user.getUsername());
        loginSessionDto.setNickname(user.getNickname());
        loginSessionDto.setUserAgent(userAgent);
        loginSessionDto.setUserIp(userIp);
        loginSessionDto.setRole(user.getRole());
        return loginSessionDto;
    }
}
