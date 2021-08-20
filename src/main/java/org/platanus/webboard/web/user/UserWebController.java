package org.platanus.webboard.web.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.web.login.argumentresolver.Login;
import org.platanus.webboard.web.login.dto.UserSessionDto;
import org.platanus.webboard.web.user.dto.UserModifyDto;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserWebController {

    public final UserService userService;

    @GetMapping(value = "/join")
    public String join(@ModelAttribute("join") User user) {
        return "user/join_form";
    }

    @PostMapping(value = "/join")
    public String createUser(@Valid @ModelAttribute("join") User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("User Controller : {}", bindingResult);
            return "user/join_form";
        }
        try {
            userService.join(user);
            log.info("User Controller #{}: join {} user", user.getId(), user.getUsername());
        } catch (Exception e) {
            log.info("User Controller: Join failed {} - {}", user.getUsername(), e.getMessage());
            bindingResult.reject("joinFailed", e.getMessage());
            return "user/join_form";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/modify")
    public String view(@ModelAttribute("modify") User modifyUser,
                       @Login UserSessionDto user) throws Exception {
        modifyUser.setId(user.getId());
        modifyUser.setUsername(user.getUsername());
        modifyUser.setDeleted(false);
        modifyUser.setEmail(userService.findById(user.getId()).getEmail());
        modifyUser.setNickname(user.getNickname());
        return "user/modify_form";
    }

    @PostMapping(value = "/modify")
    public String modifyUser(@Valid @ModelAttribute("modify") UserModifyDto modifyUser,
                             BindingResult bindingResult,
                             @Login UserSessionDto user) throws Exception {
        if (bindingResult.hasErrors()) {
            log.error("User Controller #{} : {}", user.getId(), bindingResult);
            return "user/modify_form";
        }
        modifyUser.setId(user.getId());
        modifyUser.setUsername(user.getUsername());
        if (modifyUser.getPassword().trim().length() == 0)
            modifyUser.setPassword(userService.findById(user.getId()).getPassword());
        try {
            User userFromDto = User.fromLoginSessionDto(user);
            userFromDto.setEmail(modifyUser.getEmail());
            userService.update(UserModifyDto.from(modifyUser), userFromDto);
            log.info("User Controller #{}: modify {} user", modifyUser.getId(), modifyUser.getUsername());
        } catch (Exception e) {
            log.info("User Controller: modify failed {} - {}", modifyUser.getUsername(), e.getMessage());
            bindingResult.reject("modifyFailed", e.getMessage());
            return "user/modify_form";
        }
        return "redirect:/";
    }
}
