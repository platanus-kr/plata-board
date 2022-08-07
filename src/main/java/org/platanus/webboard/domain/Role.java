package org.platanus.webboard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Role {
    private UserRole roleName;
    private Long userId;
}
