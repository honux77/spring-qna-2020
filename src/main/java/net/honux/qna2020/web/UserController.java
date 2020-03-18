package net.honux.qna2020.web;

import net.honux.qna2020.web.exception.ResourceNotFoundException;
import net.honux.qna2020.web.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

import static net.honux.qna2020.web.WebUtils.*;

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
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::userNotFound);
        Validation status = checkValidation(user, session);
        if (status.equals(Validation.NEED_LOGIN)) {
            return redirectUrl(LOGIN_URL, Validation.NEED_LOGIN.getMessage(),
                    String.format("/users/%d/updateForm", id));
        }
        //model.addAttribute("user", userRepository.getOne(id));
        model.addAttribute("user", getSessionUser(session));

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
        user.increaseAccess();
        userRepository.save(user);
        System.out.println("login success");
        if (returnTo == null) {
           return "redirect:/";
        }
        return "redirect:" + returnTo;
    }

    @PutMapping("/{id}")
    public String update(User updateUser, HttpSession session) throws IllegalAccessException {
        if (checkValidation(updateUser, session).equals(Validation.NEED_LOGIN)) {
            return redirectUrl(LOGIN_URL, Validation.NEED_LOGIN.getMessage(), null);
        }

        User user = getSessionUser(session);
        user.update(updateUser);
        //로그아웃후 재 로그인
        //안하면 세션 유저와 디비 유저가 달라짐
        sessionLogout(session);
        sessionLogin(session, user);
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
        sessionLogout(session);
        return "redirect:/";
    }

    private Validation checkValidation(User user, HttpSession session) {
        if (isNotUserLogin(session) == Validation.NEED_LOGIN) {
            return Validation.NEED_LOGIN;
        }
        if (!user.equals(getSessionUser(session))) {
            throw UnauthorizedException.noPermission();
        }
        return Validation.OK;
    }
}
