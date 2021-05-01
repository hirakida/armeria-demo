package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaServerConfig {
    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(ReactorService reactorService) {
        return builder -> builder.annotatedService(reactorService)
                                 .accessLogWriter(AccessLogWriter.combined(), false);
    }
}
