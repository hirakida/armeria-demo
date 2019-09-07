package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.service.DemoService;
import com.example.service.UserService;

import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator() {
        return builder -> {
            builder.serviceUnder("/docs", new DocService());
            builder.decorator(LoggingService.newDecorator());
            builder.accessLogWriter(AccessLogWriter.combined(), false);
            builder.annotatedService(new DemoService());
            builder.annotatedService(new UserService());
        };
    }
}
