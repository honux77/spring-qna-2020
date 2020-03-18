package net.honux.qna2020.web.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String s) {
        super(s);
    }

    public static ResourceNotFoundException userNotFound() {
        return new ResourceNotFoundException("사용자가 존재하지 않습니다.");
    }
}
