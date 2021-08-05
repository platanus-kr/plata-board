package org.platanus.webboard.web.front;

import org.platanus.webboard.auth.utils.SessionConst;
import org.platanus.webboard.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class FrontWebController {

    @GetMapping(value = "/")
    public String front(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user,
                        Model model) {
        model.addAttribute("user", user);
        return "front";
    }
}
