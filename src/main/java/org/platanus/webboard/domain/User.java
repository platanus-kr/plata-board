package org.platanus.webboard.domain;

import lombok.Data;

@Data
public class User {
    private long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private boolean deleted;

}
