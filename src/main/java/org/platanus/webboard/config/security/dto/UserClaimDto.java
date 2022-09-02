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

//    public UserClaimDto(Long id, List<Role> roles) {
//        this.id = id;
//        this.roles = roles.stream().map(v -> v.getRole().getKey()).collect(Collectors.toList());
////        this.roles = roles;
//    }
//
//    public Map<String, String> getMap() {
//        Map<String, String> ret = new HashMap<>();
//        ret.put("id", this.id.toString());
//        ret.put("roles", this.roles.toString());
//        return ret;
//    }
}
