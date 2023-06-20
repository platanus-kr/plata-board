package org.platanus.webboard.config.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * JWT 토큰 claim 을 위한 DTO<br />
 */
@Getter
@Setter
@Builder
public class UserClaimDto {
    private Long id;
    private String username;
    private List<String> roleKeys;

    @Override
    public String toString() {
        return "UserClaimDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", roleKeys=" + roleKeys +
                '}';
    }
}
