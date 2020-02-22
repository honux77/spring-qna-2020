package net.honux.qna2020.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static net.honux.qna2020.web.HttpSessionUtils.*;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

  @PostMapping("")
  public String create(@PathVariable Long questionId, String answer, HttpSession session) {

      if(isNotUserLogin(session)) {
          return "redirect:/users/loginForm";
      }

      Answer newAnswer = new Answer(questionRepository.getOne(questionId),  getSessionUser(session));
      newAnswer.setContents(answer);
      answerRepository.save(newAnswer);
      return String.format("redirect:/questions/%d", questionId);
  }
}
