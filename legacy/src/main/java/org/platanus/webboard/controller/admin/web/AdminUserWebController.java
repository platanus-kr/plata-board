package org.platanus.webboard.controller.admin.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.board.exception.ErrorDto;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.domain.UserRole;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class AdminUserWebController {
    private final UserService userService;

    @GetMapping
    public String userFront(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/user_front";
    }

    @GetMapping(value = "/{userId}/change")
    public String changeRoleConfirm(@PathVariable("userId") long userId,
                                    //@Login UserSessionDto user,
                                    @AuthenticationPrincipal Object principal,
                                    @ModelAttribute("error") ErrorDto errorDto,
                                    Model model) {
        User findUser = new User();
        try {
            findUser = userService.findById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (principal.toString().equals(findUser.getUsername())) {
            errorDto.setErrorMessage("자기 자신은 권한을 변경할 수 없습니다.");
            return "error/has_message";
        }
        model.addAttribute("user", findUser);
        return "admin/user_change";
    }

    @PostMapping(value = "/{userId}/change")
    public String changeRole(@PathVariable("userId") long userId,
                             //@Login UserSessionDto user,
                             @AuthenticationPrincipal Object principal) {
        User findUser = new User();
        try {
            findUser = userService.findById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (findUser.getRole() == UserRole.ROLE_USER) {
            userService.updateRoleByUserId(userId, UserRole.ROLE_ADMIN);
        }
        if (findUser.getRole() == UserRole.ROLE_ADMIN) {
            userService.updateRoleByUserId(userId, UserRole.ROLE_USER);
        }
        return "redirect:/admin/user";
    }

    @GetMapping(value = "/{userId}/delete")
    public String deleteConfirm(@PathVariable("userId") long userId,
                                //@Login UserSessionDto user,
                                @AuthenticationPrincipal Object principal,
                                @ModelAttribute("error") ErrorDto errorDto) {
        User findUser = new User();
        try {
            findUser = userService.findById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (principal.toString().equals(findUser.getUsername())) {
            errorDto.setErrorMessage("자기 자신은 삭제를 할 수 없습니다.");
            return "error/has_message";
        }
        return "admin/user_delete";
    }

    @PostMapping(value = "/{userId}/delete")
    public String delete(@PathVariable("userId") long userId,
                         //@Login UserSessionDto user,
                         @AuthenticationPrincipal Object principal,
                         @ModelAttribute("error") ErrorDto errorDto) {
        User findUser = new User();
        try {
            findUser = userService.findById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (principal.toString().equals(findUser.getUsername())) {
            errorDto.setErrorMessage("자기 자신은 삭제를 할 수 없습니다.");
            return "error/has_message";
        }
        try {
            userService.revoke(userService.findById(userId));
        } catch (Exception e) {
            errorDto.setErrorMessage(e.getMessage());
            return "error/has_message";
        }
        return "redirect:/admin/user";
    }
}
