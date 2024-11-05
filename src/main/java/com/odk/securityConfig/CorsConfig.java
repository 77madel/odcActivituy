package com.odk.securityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class CORSOrigin implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Autorise toutes les requêtes
                .allowedOriginPatterns("*") // Permet toutes les origines qui correspondent au motif
                .allowedOriginPatterns("http://localhost:50950/") // Permet toutes les origines qui correspondent au motif
                .allowedOriginPatterns("http://localhost:4200/") // Permet toutes les origines qui correspondent au motif
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes autorisées
                .allowedHeaders("*") // Autorise tous les en-têtes
                .allowCredentials(true); // Autorise l'envoi des cookies
    }
}
