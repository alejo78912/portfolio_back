package com.portfolio.alejandro.Config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.portfolio.alejandro.Security.ApiTokenFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${API_TOKEN}")
    private String apiToken;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuración de CORS
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF
            .addFilterAt(apiTokenFilter(), AbstractPreAuthenticatedProcessingFilter.class) // Filtro personalizado
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated()) // Autorización de solicitudes
            .build(); // Construcción de la cadena de seguridad
    }

    @Bean
    public ApiTokenFilter apiTokenFilter() {
        return new ApiTokenFilter(apiToken);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://portfolio.alejo78912.com", "https://back.alejo78912.com")); // Dominios permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true); // Permitir credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public HttpFirewall allowDoubleSlashFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true); // Permitir URLs con "//"
        return firewall;
    }
}