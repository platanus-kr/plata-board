package org.platanus.webboard.controller.web.user;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.service.UserService;
import org.platanus.webboard.utils.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

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
    public String view(@ModelAttribute("modify") User modifyUser,
                       @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user) {
        modifyUser.setId(user.getId());
        modifyUser.setUsername(user.getUsername());
        modifyUser.setDeleted(user.isDeleted());
        modifyUser.setEmail(user.getEmail());
        modifyUser.setNickname(user.getNickname());
        return "user/modifyForm";
    }

    @PostMapping(value = "/modify")
    public String modifyUser(@ModelAttribute("modify") User modifyuser,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user) {
        modifyuser.setId(user.getId());
        if (modifyuser.getPassword().trim().length() == 0)
            modifyuser.setPassword(user.getPassword());
        try {
            userService.update(modifyuser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/user/modify";
        }
        return "redirect:/";
    }
}
