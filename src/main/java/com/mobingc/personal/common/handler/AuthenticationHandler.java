package com.mobingc.personal.common.handler;

import com.mobingc.personal.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录认证拦截器配置类
 *
 * @author Mo
 * @since 2022-08-25
 */
@Component
public class AuthenticationHandler implements HandlerInterceptor {

    private final UserService userService;

    public AuthenticationHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {

        // 是否登录
        boolean isLogin = false;

        // 请求头带上令牌 Authorization :Bearer token
        String authHeader = request.getHeader("Authorization");

        // 校验Token
        if (authHeader != null) {
            // 截取token
            String token = authHeader.substring(7);
            isLogin = userService.checkToken(token);
        }

        if (!isLogin) {
            // 未登录，则响应信息
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(401);
            response.getWriter().write("未通过认证，请在登录页进行登录!");
        }

        // 不放行
        return isLogin;
    }
}

