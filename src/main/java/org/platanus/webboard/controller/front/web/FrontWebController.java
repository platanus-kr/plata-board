package org.platanus.webboard.controller.front.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FrontWebController {
    private final UserService userService;

    @GetMapping(value = "/")
    public String front(//@Login User user,
                        Model model) throws Exception {
//        User user = userService.findByUsername((Object) principal);
//        model.addAttribute("user", user);
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
