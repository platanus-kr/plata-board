package org.platanus.webboard.controller.user.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.controller.user.dto.UserModifyDto;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRole;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        user.setRole(UserRole.ROLE_USER);
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
                       //@Login UserSessionDto user,
                       @AuthenticationPrincipal Object principal) throws Exception {
        User user = userService.findByUsername(principal.toString());
        modifyUser.setId(user.getId());
        modifyUser.setUsername(user.getUsername());
        modifyUser.setDeleted(false);
        modifyUser.setEmail(userService.findById(user.getId()).getEmail());
        modifyUser.setNickname(userService.findById(user.getId()).getNickname());
        return "user/modify_form";
    }

    @PostMapping(value = "/modify")
    public String modifyUser(@Valid @ModelAttribute("modify") UserModifyDto modifyUser,
                             BindingResult bindingResult,
                             //@Login UserSessionDto user,
                             @AuthenticationPrincipal Object principal) throws Exception {
        User user = userService.findByUsername(principal.toString());
        if (bindingResult.hasErrors()) {
            log.error("User Controller #{} : {}", user.getId(), bindingResult);
            return "user/modify_form";
        }
        // 수정이 불가능한 고정된 항목.
        modifyUser.setId(user.getId());
        modifyUser.setUsername(user.getUsername());
        // 패스워드는 비어있으면 원래 패스워드를 사용한다.
        if (modifyUser.getPassword().trim().length() == 0)
            modifyUser.setPassword(userService.findById(user.getId()).getPassword());
        try {
            User findBySessionUserId = userService.findById(user.getId());
            // 수정할 수 있는 항목은 아래 두개 뿐이다.
            findBySessionUserId.setEmail(modifyUser.getEmail());
            findBySessionUserId.setNickname(modifyUser.getNickname());
            userService.update(UserModifyDto.from(modifyUser), findBySessionUserId);
            log.info("User Controller #{}: modify {} user", modifyUser.getId(), modifyUser.getUsername());
        } catch (Exception e) {
            log.info("User Controller: modify failed {} - {}", modifyUser.getUsername(), e.getMessage());
            bindingResult.reject("modifyFailed", e.getMessage());
            return "user/modify_form";
        }
        return "redirect:/";
    }
}
