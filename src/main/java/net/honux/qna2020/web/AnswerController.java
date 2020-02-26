package net.honux.qna2020.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static net.honux.qna2020.web.WebUtils.*;

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
        Question question = questionRepository.getOne(questionId);
        question.addAnswer();
        questionRepository.save(question);
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

        Question question = questionRepository.getOne(questionId);
        question.deleteAnswer();
        questionRepository.save(question);
        answerRepository.delete(answer);
        return new SimpleResponse(200, "Delete Success: ID " + id);
    }
}
