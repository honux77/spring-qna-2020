package net.honux.qna2020.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("questions", questionRepository.findAll())
                .addAttribute("num_users", userRepository.count())
                .addAttribute("num_questions", questionRepository.count());
        return "welcome";

    }
}
