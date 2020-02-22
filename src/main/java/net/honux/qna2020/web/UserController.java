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

    @GetMapping("/loginForm")
    public String loginForm(String error, String returnTo, Model model) {
        String message = null;
        model.addAttribute("error", error);
        model.addAttribute("returnTo", returnTo);
        return "/users/loginForm";
    }

    @GetMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) throws IllegalAccessException {
        User user = userRepository.getOne(id);
        Validation status = checkValidation(user, session);
        if (status.equals(Validation.NEED_LOGIN)) {
            return redirectUrl(LOGIN_URL, Validation.NEED_LOGIN.getMessage(),
                    String.format("/users/%d/updateForm", id));
        }
        model.addAttribute("user", userRepository.getOne(id));
        return "users/updateForm";
    }

    @GetMapping("/{userId}/done")
    public String registerComplete(@PathVariable Long userId, boolean update, Model model) {
        User user = userRepository.getOne(userId);
        model.addAttribute(user);
        if (update)
            model.addAttribute("update", true);
        return "users/done";
    }

    @PostMapping("/")
    public String create(User user, HttpSession session) {
        userRepository.save(user);
        sessionLogin(session, user);
        return String.format("redirect:/users/%s/done",user.getId());
    }

    @PostMapping("/login")
    public String login(String email, String password, String returnTo, HttpSession session) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return redirectUrl(LOGIN_URL,"Email not found!", returnTo);
        }

        if (user.notMatchPassword(password)) {
            return redirectUrl(LOGIN_URL, "Wrong password!", returnTo);
        }

        sessionLogin(session, user);
        System.out.println("login success");
        if (returnTo == null) {
           return "redirect:/";
        }
        return "redirect:" + returnTo;
    }

    @PutMapping("/{userId}")
    public String update(@PathVariable Long userId, User updateUser, HttpSession session) throws IllegalAccessException {
        System.out.println(updateUser);
        if (checkValidation(updateUser, session).equals(Validation.NEED_LOGIN)) {
            return redirectUrl(LOGIN_URL, Validation.NEED_LOGIN.getMessage(), null);
        }

        User user = getSessionUser(session);
        user.update(updateUser);
        userRepository.save(user);
        return String.format("redirect:/users/%d/done?update=true", user.getId());
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

    private Validation checkValidation(User user, HttpSession session) throws IllegalAccessException {
        if (isNotUserLogin(session)) {
            return Validation.NEED_LOGIN;
        }
        if (!user.equals(getSessionUser(session))) {
            throw new IllegalAccessException(Validation.FAIL.toString());
        }
        return Validation.OK;
    }
}
