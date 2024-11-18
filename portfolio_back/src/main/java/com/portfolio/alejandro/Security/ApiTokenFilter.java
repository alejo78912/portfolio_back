package com.portfolio.alejandro.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.http.HttpServletResponse;

public class ApiTokenFilter extends OncePerRequestFilter {

    @Value("${API_TOKEN}")
    private String apiToken; // Obtener el token desde variable de entorno
    //proof
    
    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response,
            jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  

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