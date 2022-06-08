package br.com.upper.product.api.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.upper.product.api.modules.jwt.service.JwtService;
import feign.Request;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isObtions(request)) {
            return true;
        }
        var authorization = request.getHeader(AUTHORIZATION);
        jwtService.validateAuthorization(authorization);
        return true;
    }

    private boolean isObtions(HttpServletRequest request) {
        return Request.HttpMethod.OPTIONS.name().equals(request.getMethod());
    }
}
