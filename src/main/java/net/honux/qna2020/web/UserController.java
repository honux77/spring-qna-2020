package net.honux.qna2020.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @PostMapping("/create")
    public String create(String email, String name, String password1, String password2, Model model) {
        System.out.printf("%s %s %s %s\n", email, name, password1, password2);
        model.addAttribute("email", email);
        model.addAttribute("name", name);
        model.addAttribute("password1", password1);
        model.addAttribute("password2", password2);
        return "create";
    }
}
