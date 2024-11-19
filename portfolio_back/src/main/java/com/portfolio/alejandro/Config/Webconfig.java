package com.portfolio.alejandro.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Webconfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**") // Aplica la configuración a todas las rutas
            .allowedOrigins(
                "https://portfolio.alejo78912.com",
                "https://back.alejo78912.com"
            ) // Permitir solicitudes de todos los orígenes
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
            .allowedHeaders("Authorization") // Permitir todos los headers
            .allowCredentials(true); // Permitir el envío de credenciales (cookies, headers de autorización)
    }
}