package net.honux.qna2020.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String registerComplete(@PathVariable Long userId, boolean update, Model model) {
        User user = userRepository.findById(userId).get();
        model.addAttribute(user);
        if(update)
            model.addAttribute("update", true);
        return "users/done";
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "users/updateForm";
    }

    @PostMapping("/new")
    public String create(User user, Model model) {
        userRepository.save(user);
        return "redirect:/users/new/done/" + user.getId();
    }

    //@PutMapping not working
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, User updateUser, Model model) {
        User user = userRepository.findById(id).get();
        user.update(updateUser);
        userRepository.save(user);
        return "redirect:/users/new/done/" + user.getId()  + "?update=true";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/list";
    }
}
