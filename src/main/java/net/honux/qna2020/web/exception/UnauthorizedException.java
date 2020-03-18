package net.honux.qna2020.web.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String s) {
        super(s);
    }

    public static UnauthorizedException noPermission() {
        return new UnauthorizedException("접근 권한이 없습니다.");
    }
}
