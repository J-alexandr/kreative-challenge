package com.ekreative.hackathon.challenge.filter;

import com.ekreative.hackathon.challenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HeaderBasedSecurityFilter extends GenericFilterBean {
    @Autowired
    private UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = ((HttpServletRequest) request).getRequestURI();
        if (!path.equals("/register")) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            try {
                String uuid = httpRequest.getHeader("UUID");
                userService.findByUuid(uuid);
            } catch (Exception e) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
            }
        }
        chain.doFilter(request, response);
    }
}
