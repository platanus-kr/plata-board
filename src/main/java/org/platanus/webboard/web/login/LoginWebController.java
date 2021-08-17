package org.platanus.webboard.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.auth.utils.SessionConst;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.web.login.dto.LoginUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginWebController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("login") LoginUserDto login) {
        return "login/login_form";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("login") LoginUserDto login,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {
        User loginUser;

        if (bindingResult.hasErrors()) {
            log.error("error = {}", bindingResult);
            return "login/login_form";
        }

        try {
            loginUser = loginService.login(login.getUsername(), login.getPassword());
            log.info("Login Controller: Login {}", login.getUsername());
        } catch (Exception e) {
            bindingResult.reject("loginFailed", "로그인에 실패 했습니다.");
            log.info("Login Controller: Login failed {} - {}", login.getUsername(), e.getMessage());
            return "login/login_form";
        }
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
        return "redirect:" + redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
