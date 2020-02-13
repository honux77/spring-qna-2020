package net.honux.qna2020.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String registerForm() {
        return "/users/form";
    }

    @GetMapping("/done/{userId}")
    public String registerComplete(@PathVariable Long userId, boolean update, Model model) {
        User user = userRepository.findById(userId).get();
        model.addAttribute(user);
        if(update)
            model.addAttribute("update", true);
        return "users/done";
    }

    @GetMapping("/loginForm")
    public String loginForm(String error, Model model) {
        String message = null;
        if (error == null ) {
            error = "";
        }
        switch(error) {
            case "email":
                message = "Email not found!";
                break;
            case "password":
                message = "Wrong password";
            case "login":
                message = "please login";
        }

        model.addAttribute("error", message);
        return "/users/loginForm";
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) throws IllegalAccessException {
        User user = userRepository.findById(id).get();
        User sessionedUser = (User) session.getAttribute("session-user");
        if (sessionedUser == null) {
            return "redirect:/users/loginForm?error=login";
        }
        if (!user.getId().equals(sessionedUser.getId())) {
            throw new IllegalAccessException("you don't have permission to update user " + id);
        }
            model.addAttribute("user", userRepository.findById(id).get());
        return "users/updateForm";
    }

    @PostMapping("/login")
    public String login(String email, String password, HttpSession session) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("Email not found");
            return "redirect:/users/loginForm?error=email";
        }
        if (!password.equals(user.getPassword())) {
            System.out.println("Wrong password");
            return "redirect:/users/loginForm?error=password";
        }
        session.setAttribute("session-user", user);
        System.out.println("login success");
        return "redirect:/";
    }

    @PostMapping("/")
    public String create(User user, Model model) {
        userRepository.save(user);
        return "redirect:/users/done/" + user.getId();
    }

    //@PutMapping not working
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, User updateUser, Model model, HttpSession session) throws IllegalAccessException {
        User sessionedUser = (User) session.getAttribute("session-user");
        if (sessionedUser == null) {
            return "redirect:/users/loginForm?error=login";
        }

        User user = userRepository.findById(id).get();
        if (!user.getId().equals(sessionedUser.getId())) {
            throw new IllegalAccessException("you don't have permission to update user " + id);
        }
        user.update(updateUser);
        userRepository.save(user);
        return "redirect:/users/done/" + user.getId()  + "?update=true";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/list";
    }

    //logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("session-user");
        return "redirect:/";
    }
}
