package net.honux.qna2020.web;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String SESSION_USER_KEY = "session-user";
    public static final String LOGIN_URL = "/users/loginForm";

    public static User getSessionUser(HttpSession session) {
        return (User) session.getAttribute(SESSION_USER_KEY);
    }

    public static boolean isNotUserLogin(HttpSession session) {
        return getSessionUser(session) == null;
    }

    public static void sessionLogin(HttpSession session, User user) {
        session.setAttribute(SESSION_USER_KEY, user);
    }

    public static void sessionLogout(HttpSession session) { session.removeAttribute(SESSION_USER_KEY); }

    public static String redirectUrl(String url, String error, String returnTo) {
        String returnUrl = String.format("redirect:%s?", url);
        if (error != null) {
            returnUrl += String.format("error=%s&", error);
        }
        if (returnTo != null) {
            returnUrl += String.format("returnTo=%s", returnTo);
        }
        return returnUrl;
    }
}
