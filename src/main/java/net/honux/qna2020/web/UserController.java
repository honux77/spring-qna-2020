package net.honux.qna2020.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @PostMapping("/create")
    public String create(User user, Model model) {
        System.out.println(user);
        model.addAttribute("user", user);
        return "create";
    }
}
