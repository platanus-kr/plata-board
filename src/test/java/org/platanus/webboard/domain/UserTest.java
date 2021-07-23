package org.platanus.webboard.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void domainTest() {
        User user = new User();
        user.setId(1L);
        user.setUsername("Spring");
        user.setPassword("password");
        user.setEmail("platanus.kr@gmail.com");
        assertEquals(user.getUsername(), "Spring");
    }
}
