package net.honux.qna2020.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static net.honux.qna2020.web.HttpSessionUtils.*;

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
        User user = userRepository.getOne(userId);
        model.addAttribute(user);
        if (update)
            model.addAttribute("update", true);
        return "users/done";
    }

    @GetMapping("/loginForm")
    public String loginForm(String error, Model model) {
        String message = null;
        if (error == null) {
            error = "";
        }
        switch (error) {
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

    @GetMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) throws IllegalAccessException {
        User user = userRepository.getOne(id);
        if (isNotUserLogin(session)) {
            return "redirect:/users/loginForm?error=login";
        }
        User sessionUser = getSessionUser(session);
        if (!user.equals(sessionUser)) {
            throw new IllegalAccessException("you don't have permission to update user " + id);
        }
        model.addAttribute("user", userRepository.getOne(id));
        return "users/updateForm";
    }

    @PostMapping("/login")
    public String login(String email, String password, HttpSession session) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("Email not found");
            return "redirect:/users/loginForm?error=email";
        }
        if (user.notMatchPassword(password)) {
            System.out.println("Wrong password");
            return "redirect:/users/loginForm?error=password";
        }
        sessionLogin(session, user);
        System.out.println("login success");
        return "redirect:/";
    }

    @PostMapping("/")
    public String create(User user, HttpSession session) {
        userRepository.save(user);
        sessionLogin(session, user);
        return "redirect:/users/done/" + user.getId();
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updateUser, HttpSession session) throws IllegalAccessException {
        if (isNotUserLogin(session)) {
            return "redirect:/users/loginForm?error=login";
        }

        User sessionUser = getSessionUser(session);

        User user = userRepository.getOne(id);

        if (!user.getId().equals(sessionUser.getId())) {
            throw new IllegalAccessException("you don't have permission to update user " + id);
        }
        user.update(updateUser);
        userRepository.save(user);
        return "redirect:/users/done/" + user.getId() + "?update=true";
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
