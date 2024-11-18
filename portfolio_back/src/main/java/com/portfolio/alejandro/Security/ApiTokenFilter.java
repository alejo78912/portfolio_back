package com.portfolio.alejandro.Security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApiTokenFilter extends OncePerRequestFilter {

    private final String apiToken;

    public ApiTokenFilter(String apiToken) {
        this.apiToken = apiToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remover "Bearer "

            if (token.equals(apiToken)) {
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken("user", null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("Forbidden: Invalid API Token");
                return;
            }
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("Bad Request: Missing or Invalid Authorization header");
            return;
        }

        filterChain.doFilter(request, response);
    }
}