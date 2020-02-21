package com.example.frontend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class FrontendServerConfig {
    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(FrontendService frontendService) {
        return builder -> builder.annotatedService(frontendService);
    }
}
