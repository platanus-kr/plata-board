package org.platanus.webboard.controller.web.login;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.service.LoginService;
import org.platanus.webboard.utils.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") Login login) {
        return "/login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Login login, HttpServletRequest request) {
        User loginUser;
        try {
            loginUser = loginService.login(login.getUsername(), login.getPassword());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "/login/loginForm";
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);

        return "redirect:/";

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
