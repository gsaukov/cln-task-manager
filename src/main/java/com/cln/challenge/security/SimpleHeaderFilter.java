package com.cln.challenge.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//TODO i ve made authsecurity on/off swith for ease of swagger testing.
@Component
public class SimpleHeaderFilter extends OncePerRequestFilter {

    private final String HEADER_NAME = "Cln-Auth";
    private final String HEADER_VALUE = "totally_secret";
    private final String NOT_AUTHORIZED = "Not authorized";
    private final String OPTIONS = "OPTIONS";

    @Value("${clnTaskManager.authheader.disabled}")
    private Boolean authHeaderDisabled;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return authHeaderDisabled;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // OPTIONS should always work
        if (request.getMethod().equals(OPTIONS)) {
            filterChain.doFilter(request, response);
            return;
        }

        String val = request.getHeader(HEADER_NAME);
        if (val == null || !val.equals(HEADER_VALUE)) {
            response.setStatus(401);
            response.getWriter().append(NOT_AUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
