package com.kchandrakant.learning;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    private final Logger log = LogManager.getLogger(getClass());

    private static final String AUTH_HEADER_PARAMETER_AUTHORIZATION = "Authorization";

    private static final String AUTH_HEADER_PARAMETER_BEARER = "Bearer ";

    @Autowired
    SymmetricJweHandler symmetricJweHandler;

    @Autowired
    AsymmetricJweHandler asymmetricJweHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        log.info("Pre Handle Interceptor" + request.getRequestURI());

        String jweAuthToken = null;

        try {
            jweAuthToken = request.getHeader(AUTH_HEADER_PARAMETER_AUTHORIZATION).replace(AUTH_HEADER_PARAMETER_BEARER, "");
            log.info("JWE Auth Token: " + jweAuthToken);
            symmetricJweHandler.validateJweToken(jweAuthToken);
            response.setStatus(HttpStatus.ACCEPTED.value());
            return true;
        } catch (AuthenticationException e) {
            log.error("Error occurred while authenticating request : " + e.getMessage());
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader("ValidJwe", symmetricJweHandler.getJweToken());
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        log.info("Post Handle Interceptor" + request.getRequestURI());

    }

}
