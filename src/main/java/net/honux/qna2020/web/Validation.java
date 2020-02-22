package net.honux.qna2020.web;

public enum Validation {
    OK("OK"),
    NEED_LOGIN("You should sign-in."),
    FAIL("You don't have permission to access");

    private String message;

    private Validation(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
