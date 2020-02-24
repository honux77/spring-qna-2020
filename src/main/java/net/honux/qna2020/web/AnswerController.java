package net.honux.qna2020.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

import static net.honux.qna2020.web.HttpSessionUtils.*;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String answer, HttpSession session) {
        if (isNotUserLogin(session) == Validation.NEED_LOGIN) {
            return null;
        }

        Answer newAnswer = new Answer(questionRepository.getOne(questionId), getSessionUser(session), answer);
        return answerRepository.save(newAnswer);
    }

    @DeleteMapping("{id}")
    public SimpleResponse delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (isNotUserLogin(session) == Validation.NEED_LOGIN) {
            return new SimpleResponse(404, "need login");
        }
        Answer answer = answerRepository.getOne(id);
        if (!answer.matchAuthor(getSessionUser(session))) {
            return new SimpleResponse(403, "you don't have permission");

        }
        answerRepository.delete(answer);
        return new SimpleResponse(200, "Delete Success: ID " + id);
    }
}
