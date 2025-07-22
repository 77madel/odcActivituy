package com.odk.securityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("https://odc-web-6afd.onrender.com", "http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Ajoutez PATCH ici
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

}


