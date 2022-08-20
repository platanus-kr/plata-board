package org.platanus.webboard.utils.migrate.fromV1;

import lombok.Getter;
import lombok.Setter;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRole;

@Getter
@Setter
public class MigrationUser extends User {

    private String v1UserRole;
    private UserRole v2UserRole;

    public static User toV2User(MigrationUser migUser, String v2Password, UserRole v2UserRole) {
        return User.builder()
                .id(migUser.getId())
                .username(migUser.getUsername())
                .password(v2Password)
                .nickname(migUser.getNickname())
                .email(migUser.getEmail())
                .deleted(migUser.isDeleted())
                .role(v2UserRole)
                .build();
    }
}
