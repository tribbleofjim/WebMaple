package org.webmaple.admin.aop;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lyifee
 * on 2021/2/2
 */
public class LoginAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        return checkAuth(cookies);
    }

    private boolean checkAuth(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if ("user".equals(cookie.getName())) {
                return true;
            }
        }
        return false;
    }
}
