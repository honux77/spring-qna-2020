package net.honux.qna2020.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        if(isNotUserLogin(session) == Validation.NEED_LOGIN) {
            return null;
        }

        Answer newAnswer = new Answer(questionRepository.getOne(questionId),  getSessionUser(session), answer);
        return answerRepository.save(newAnswer);
    }
}
