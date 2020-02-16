package net.honux.qna2020.web;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String SESSION_USER_KEY = "session-user";

    public static User getSessionUser(HttpSession session) {
        return (User) session.getAttribute(SESSION_USER_KEY);
    }

    public static boolean isNotUserLogin(HttpSession session) {
        return getSessionUser(session) == null;
    }
}