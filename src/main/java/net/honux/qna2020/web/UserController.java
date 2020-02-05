package net.honux.qna2020.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/new")
    public String registerForm() {
        return "/users/new";
    }

    @GetMapping("/new/done/{userId}")
    public String registerComplete(@PathVariable Long userId, Model model) {
        User user = userRepository.findById(userId).get();
        model.addAttribute(user);
        return "users/done";
    }

    @PostMapping("/new")
    public String create(User user, Model model) {
        userRepository.save(user);
        return "redirect:/users/new/done/" + user.getId();
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/list";
    }
}
