package com.webmaple.admin.aop;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lyifee
 * on 2021/2/2
 */
public class LoginAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("webmaple_user_token");
        return checkAuth(token);
    }

    private boolean checkAuth(String token) {
        return token != null;
    }
}
