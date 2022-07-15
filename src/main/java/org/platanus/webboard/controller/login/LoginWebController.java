package org.platanus.webboard.controller.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.auth.utils.SessionConst;
import org.platanus.webboard.controller.login.dto.UserLoginDto;
import org.platanus.webboard.controller.login.dto.UserSessionDto;
import org.platanus.webboard.domain.User;
import org.platanus.webboard.utils.IPUtils;
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
    public String loginForm(@ModelAttribute("login") UserLoginDto login) {
        return "login/login_form";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("login") UserLoginDto userLoginDto,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {
        User loginUser;
        if (bindingResult.hasErrors()) {
            log.error("error = {}", bindingResult);
            return "login/login_form";
        }
        try {
            loginUser = loginService.login(userLoginDto.getUsername(), userLoginDto.getPassword());
            log.info("Login Controller: Login {}", userLoginDto.getUsername());
        } catch (Exception e) {
            bindingResult.reject("loginFailed", "로그인에 실패 했습니다.");
            log.info("Login Controller: Login failed {} - {}", userLoginDto.getUsername(), e.getMessage());
            return "login/login_form";
        }
        UserSessionDto userSessionDto = UserSessionDto.from(loginUser,
                request.getHeader("user-agent"), IPUtils.getRemoteAddr(request));
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_USER, userSessionDto);
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

    @GetMapping("/session_error")
    public String sessionError(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "error/has_session_error";
    }
}
