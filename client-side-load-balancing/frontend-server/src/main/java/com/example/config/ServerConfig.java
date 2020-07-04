package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.FrontendService;

import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ServerConfig {
    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(FrontendService frontendService) {
        return builder -> builder.annotatedService(frontendService);
    }
}
