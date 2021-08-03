package org.platanus.webboard.controller.web.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {

    @GetMapping(value = "/")
    public String front() {
        return "front";
    }
}
