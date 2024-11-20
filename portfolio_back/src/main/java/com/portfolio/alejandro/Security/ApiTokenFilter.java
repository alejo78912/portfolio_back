package com.portfolio.alejandro.Security;

import java.io.IOException;
import java.util.Collections;

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
                // Si el token es válido, crea la autenticación y establece el contexto de seguridad
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken("user", null, Collections.emptyList());  // Puedes añadir roles aquí
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Si el token es inválido, responde con error 403 Forbidden en formato JSON
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Forbidden: Invalid API Token\"}");
                return;
            }
        } else {
            // Si no hay token en el encabezado, responde con error 400 Bad Request en formato JSON
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Bad Request: Missing or Invalid Authorization header\"}");
            return;
        }

        // Continúa con el siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }
}
