package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.HelloService;

import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ServerConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(HelloService helloService) {
        return builder -> builder.annotatedService(helloService);
    }
}
