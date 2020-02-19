package net.honux.qna2020.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) throws IllegalAccessException {
        if (isNotUserLogin(session)) {
            return "redirect:/users/loginForm?error=login";
        }

        Question updateQuestion = questionRepository.getOne(id);

        User sessionUser = getSessionUser(session);
        if (!updateQuestion.matchAuthor(sessionUser)) {
            throw new IllegalAccessException("You don't have permission to update Question " + id);
        }
        model.addAttribute("question", updateQuestion);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question question, HttpSession session) throws IllegalAccessException {
        if (isNotUserLogin(session)) {
            return "redirect:/users/loginForm?error=login";
        }

        Question updateQuestion = questionRepository.getOne(id);

        User sessionUser = getSessionUser(session);
        if (!updateQuestion.matchAuthor(sessionUser)) {
            throw new IllegalAccessException("You don't have permission to update Question " + id);
        }

        updateQuestion.update(question);
        questionRepository.save(updateQuestion);
        return String.format("redirect:/questions/%d/", id);
    }


    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model, HttpSession session) {
        if(isNotUserLogin(session)) {
            return "redirect:/users/loginForm?error=login";
        }
        Question question = questionRepository.getOne(id);
        model.addAttribute("question", questionRepository.getOne(id));
        if (question.matchAuthor(getSessionUser(session))) {
            //System.out.println("My Question");
            model.addAttribute("own", true);
        }
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
