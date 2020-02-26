package net.honux.qna2020.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/forward/{id}")
    public String forwardTest(@PathVariable Long id,  Model model) {
        model.addAttribute("v1", "v1");
        System.out.println("forwardTest1");
        return "forward:/test/forward2";
    }

    @GetMapping("/forward2")
    public String  forwardTest2(HttpServletRequest req, Model model) {
        System.out.println("forwardTest2");
        System.out.println(req.getRequestURL());
        model.addAttribute("v2", "v2");
        return "/test/forward";
    }

}
