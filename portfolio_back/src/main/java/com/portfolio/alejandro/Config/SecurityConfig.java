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

    @Value("${API_TOKEN}")
    private String apiToken;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuración de CORS
            .csrf().disable() // Desactiva CSRF para permitir solicitudes sin autenticación de formularios
            .addFilterAt(apiTokenFilter(), AbstractPreAuthenticatedProcessingFilter.class)
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/contacts/**").authenticated() // Requiere autenticación
                    .requestMatchers("/public/**").permitAll() // Acceso público
                    .anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación
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
        // Configuración de CORS
        configuration.setAllowedOrigins(List.of("https//:portfolio.alejo78912.com")); // Permitir todos los orígenes
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        configuration.setAllowedHeaders(List.of("Authorization")); // Headers permitidos
        configuration.setExposedHeaders(List.of("Authorization")); // Headers expuestos
        configuration.setAllowCredentials(true); // Permitir credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplicar configuración a todas las rutas
        return source;
    }
}