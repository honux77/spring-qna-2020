package net.honux.qna2020.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

import static net.honux.qna2020.web.HttpSessionUtils.*;

@Controller
@RequestMapping("/questions/")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        if (isNotUserLogin(session)) {
            return "redirect:/users/loginForm?error=login";
        }
        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model, HttpSession session) {
        if(isNotUserLogin(session)) {
            return "redirect:/users/loginForm?error=login";
        }
        Question question = questionRepository.getOne(id);
        model.addAttribute("question", questionRepository.getOne(id));
        return "/qna/question";
    }

    @PostMapping("")
    public String create(Question question, HttpSession session) {
        if (isNotUserLogin(session)) {
            return "redirect:/users/loginForm?error=login";

        }
        question.setAuthor(getSessionUser(session));
        questionRepository.save(question);

        return "redirect:/";
    }
}
