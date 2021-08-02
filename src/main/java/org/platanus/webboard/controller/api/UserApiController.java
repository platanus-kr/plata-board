package org.platanus.webboard.controller.api;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;

    @GetMapping(value = "/list")
    public List<User> findAll() throws Exception {
        return userService.findAll();
    }

    @PostMapping()
    public User join(@RequestBody User user) throws Exception {
        return userService.join(user);
    }
}
