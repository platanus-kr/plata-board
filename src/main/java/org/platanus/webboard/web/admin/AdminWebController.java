package org.platanus.webboard.web.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.web.login.argumentresolver.Login;
import org.platanus.webboard.web.login.dto.UserSessionDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminWebController {

    @GetMapping
    public String front(@Login UserSessionDto user, Model model) {
        model.addAttribute("user", user);
        return "admin/front";
    }
}
