package com.portfolio.alejandro.Config;

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

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors()  // Habilita CORS en la configuración de seguridad
            .and()
            .csrf().disable()  // Desactiva CSRF para APIs
            .addFilterAt(new ApiTokenFilter(), AbstractPreAuthenticatedProcessingFilter.class)
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/contacts/**").authenticated()  // Protección de todas las rutas de contactos
                    .requestMatchers("/public/**").permitAll()  // Rutas públicas sin protección
                    .anyRequest().authenticated()  // Asegura que cualquier otra solicitud sea autenticada
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));  // Permitir solicitudes desde Angular
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
