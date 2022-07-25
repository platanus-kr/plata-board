package org.platanus.webboard.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;

//    @GetMapping(value = "/list")
//    public List<User> findAll() throws Exception {
//        return userService.findAll();
//    }
//
//    @PostMapping()
//    public User join(@RequestBody User user) throws Exception {
//        return userService.join(user);
//    }
}
