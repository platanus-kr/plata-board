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
//        return "front";
        return "redirect:/board/1";
    }

    @GetMapping(value = "/fragment/main")
    public String test() {
        return "fragment_est/basic/main";
    }

    @GetMapping(value = "/fragment/layout")
    public String test2() {
        return "fragment_test/layout/layout";
    }

    @GetMapping(value = "/fragment/extend")
    public String test3() {
        return "fragment_test/extend/extendMain";
    }
}
