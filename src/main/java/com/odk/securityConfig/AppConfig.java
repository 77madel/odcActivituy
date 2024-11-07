package com.odk.securityConfig;

import com.odk.Entity.visitor.VisitorLogger;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class AppConfig implements WebMvcConfigurer {

    private VisitorLogger visitorLogger;

    @Override
    public void addInterceptors(@SuppressWarnings("null") InterceptorRegistry registry) {
        registry.addInterceptor(visitorLogger);
    }
}