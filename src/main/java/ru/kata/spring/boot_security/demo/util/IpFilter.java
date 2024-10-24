package ru.kata.spring.boot_security.demo.util;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class IpFilter extends OncePerRequestFilter {

    private static final List<String> BANNED_IPS  = Arrays.asList(
            "127.0.0.1",
            "0:0:0:0:0:0:0:1"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String getRemoteIp = request.getRemoteAddr();

        if (BANNED_IPS.contains(getRemoteIp)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Доступ запрещен для ip: " + getRemoteIp);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
