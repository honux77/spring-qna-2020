package net.honux.qna2020.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/create")
    public String create(){
        return "create";
    }
}
