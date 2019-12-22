package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ServerService;

import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ServerConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(ServerService serverService) {
        return builder -> builder.annotatedService(serverService);
    }
}
