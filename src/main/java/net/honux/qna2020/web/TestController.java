package net.honux.qna2020.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("/{id}")
    public String test404(@PathVariable Long id, Model model){
        if(id == 404) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Such ID HA HA HA");
        }
        model.addAttribute("id", id);
        return "/test/404";
    }

}
