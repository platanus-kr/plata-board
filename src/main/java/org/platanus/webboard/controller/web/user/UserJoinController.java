package org.platanus.webboard.controller.web.user;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserJoinController {

    public final UserService userService;

    @GetMapping(value = "/join")
    public String join(@ModelAttribute("join") User user) {
        return "user/joinForm";
    }

    @PostMapping(value = "/join")
    public String createUser(@ModelAttribute("join") User user) {
        try {
            userService.join(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/join";
        }
        return "redirect:/";

    }
}
