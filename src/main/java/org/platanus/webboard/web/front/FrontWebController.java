package org.platanus.webboard.web.front;

import org.platanus.webboard.domain.User;
import org.platanus.webboard.web.login.argumentresolver.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontWebController {
    @GetMapping(value = "/")
    public String front(@Login User user,
                        Model model) {
        model.addAttribute("user", user);
        return "front";
    }
}
