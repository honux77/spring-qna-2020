package net.honux.qna2020.web;

import net.honux.qna2020.web.exception.ResourceNotFoundException;
import net.honux.qna2020.web.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CommonErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String status404(ResourceNotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error/error";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthorizedException.class)
    public String statusForbidden(UnauthorizedException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error/error";
    }
}
