package org.platanus.webboard.web.user;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.web.login.argumentresolver.Login;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserWebController {

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

    @GetMapping(value = "/modify")
    public String view(@ModelAttribute("modify") User modifyUser, @Login User user) {
        modifyUser.setId(user.getId());
        modifyUser.setUsername(user.getUsername());
        modifyUser.setDeleted(user.isDeleted());
        modifyUser.setEmail(user.getEmail());
        modifyUser.setNickname(user.getNickname());
        return "user/modifyForm";
    }

    @PostMapping(value = "/modify")
    public String modifyUser(@ModelAttribute("modify") User modifyUser, @Login User user) {
        modifyUser.setId(user.getId());
        if (modifyUser.getPassword().trim().length() == 0)
            modifyUser.setPassword(user.getPassword());
        try {
            userService.update(modifyUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/user/modify";
        }
        return "redirect:/";
    }
}
