package org.platanus.webboard.web.login.dto;

import lombok.Data;
import org.platanus.webboard.domain.User;

@Data
public class UserSessionDto {
    private long id;
    private String username;
    private String nickname;
    private String userAgent;
    private String email;
    private String userIp;

    public static UserSessionDto from(User user, String userAgent, String userIp) {
        UserSessionDto loginSessionDto = new UserSessionDto();
        loginSessionDto.setId(user.getId());
        loginSessionDto.setUsername(user.getUsername());
        loginSessionDto.setNickname(user.getNickname());
        loginSessionDto.setUserAgent(userAgent);
        loginSessionDto.setUserIp(userIp);
        return loginSessionDto;
    }
}
