package com.portfolio.alejandro.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.portfolio.alejandro.Security.ApiTokenFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    
    private String apiToken = "W7jksi91jsiwqoiqwi189wjqiwjwiqwq9j92u192ji21y9rhqwouE2u9kL8ZpVdQk";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuración de CORS
            .csrf().disable() // Desactiva CSRF
            .addFilterAt(apiTokenFilter(), AbstractPreAuthenticatedProcessingFilter.class) // Filtro personalizado
            .authorizeRequests(authorizeRequests -> 
                authorizeRequests.anyRequest().authenticated() // Todas las rutas requieren autenticación
            );

        return http.build();
    }

    @Bean
    public ApiTokenFilter apiTokenFilter() {
        return new ApiTokenFilter(apiToken);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://portfolio.alejo78912.com", "https://back.alejo78912.com")); // Cambia * por dominios específicos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true); // Permitir credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}