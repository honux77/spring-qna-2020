package net.honux.qna2020.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }
}
