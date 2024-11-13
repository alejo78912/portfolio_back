package com.portfolio.alejandro.Security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.http.HttpServletResponse;

public class ApiTokenFilter extends OncePerRequestFilter {

    private static final String API_TOKEN = "W7j#E2u9kL8ZpV!dQk@xR6f5T3yF$zB0P2zM8Gv0F9V";  
    
    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response,
            jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  

            if (token.equals(API_TOKEN)) {
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
