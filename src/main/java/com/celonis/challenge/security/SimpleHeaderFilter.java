package com.celonis.challenge.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//TODO i ve made authsecurity on/off swith for ease of swagger testing.
//TODO a bit confused here, doesn't preflight requests in spring work before any filters by default?
@Component
public class SimpleHeaderFilter extends OncePerRequestFilter {

    private final String HEADER_NAME = "Celonis-Auth";
    private final String HEADER_VALUE = "totally_secret";

    @Value("${clnTaskManager.authheader.disabled}")
    private Boolean authHeaderDisabled;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return authHeaderDisabled;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // OPTIONS should always work
        if (request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        String val = request.getHeader(HEADER_NAME);
        if (val == null || !val.equals(HEADER_VALUE)) {
            response.setStatus(401);
            response.getWriter().append("Not authorized");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
