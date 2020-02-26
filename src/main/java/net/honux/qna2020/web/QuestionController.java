package net.honux.qna2020.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

import static net.honux.qna2020.web.WebUtils.*;

@Controller
@RequestMapping("/questions/")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        if (isNotUserLogin(session) == Validation.NEED_LOGIN) {
            return redirectUrl(LOGIN_URL, Validation.NEED_LOGIN.getMessage(),"/questions/form");
        }
        return "/qna/form";
    }

    @GetMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) throws IllegalAccessException {

        Question updateQuestion = getQuestionOr404(id);
        Validation validation = checkValidation(updateQuestion, session);
        if (validation == Validation.NEED_LOGIN) {
            return redirectUrl(LOGIN_URL, validation.getMessage(),
                    String.format("/questions/%d/updateForm", id));
        }

        User sessionUser = getSessionUser(session);
        model.addAttribute("question", updateQuestion);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question question, HttpSession session) throws IllegalAccessException {
        if (isNotUserLogin(session) == Validation.NEED_LOGIN) {
            return redirectUrl(LOGIN_URL, Validation.NEED_LOGIN.getMessage(),
                    String.format("/questions/%d", id));
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
        if(isNotUserLogin(session) == Validation.NEED_LOGIN) {
            return redirectUrl(LOGIN_URL, Validation.NEED_LOGIN.getMessage()
            ,String.format("/questions/%d", id));
        }
        Question question = questionRepository.getOne(id);
        model.addAttribute("question", question);

        if (question.matchAuthor(getSessionUser(session))) {
            model.addAttribute("own", true);
        }
        return "/qna/question";
    }

    @PostMapping("")
    public String create(Question question, HttpSession session) {
        if (isNotUserLogin(session) == Validation.NEED_LOGIN) {
            return redirectUrl(LOGIN_URL, Validation.NEED_LOGIN.getMessage(), "/questions/form");
        }
        question.setAuthor(getSessionUser(session));
        questionRepository.save(question);

        return "redirect:/";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, HttpSession session) throws IllegalAccessException {
        if (isNotUserLogin(session) == Validation.NEED_LOGIN) {
            return redirectUrl(LOGIN_URL, Validation.NEED_LOGIN.getMessage(),
                    String.format("/questions/%d", id));
        }
        Question question = questionRepository.getOne(id);
        if (!question.matchAuthor(getSessionUser(session))) {
            throw new IllegalAccessException("You don't have permission to delete question %d" + id);
        }

        //remove all answers
        //cascade 옵션을 사용하고 싶었는데 잘 안 됨
        for (Answer answer:question.getAnswers()) {
            answerRepository.delete(answer);
        }

        questionRepository.delete(question);
        return "redirect:/";
    }

    private Question getQuestionOr404(Long id) throws ResponseStatusException {
        return questionRepository.findById(id)
                .orElseThrow(()-> (new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found")));
    }

    private Validation checkValidation(Question question, HttpSession session) throws ResponseStatusException {
        if (isNotUserLogin(session) == Validation.NEED_LOGIN) {
            return Validation.NEED_LOGIN;
        }

        if (!question.matchAuthor(getSessionUser(session))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, Validation.FAIL.getMessage());
        }
        return Validation.OK;
    }
}
