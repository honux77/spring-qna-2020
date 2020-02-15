package net.honux.qna2020.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static net.honux.qna2020.web.HttpSessionUtils.*;

@Controller
@RequestMapping("/questions/")
public class QuestionController {

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        if (isNotUserLogin(session)) {
            return "redirect:/users/loginForm?error=login";
        }
        return "/qna/form";
    }
}
