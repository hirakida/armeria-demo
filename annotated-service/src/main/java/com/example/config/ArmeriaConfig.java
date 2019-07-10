package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.service.ApiService;
import com.example.service.SseService;

import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.throttling.RateLimitingThrottlingStrategy;
import com.linecorp.armeria.server.throttling.ThrottlingHttpService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator() {
        return builder -> {
            builder.serviceUnder("/docs", new DocService());
            builder.decorator(LoggingService.newDecorator());
            builder.decorator(ThrottlingHttpService.newDecorator(new RateLimitingThrottlingStrategy<>(10.0)));
            builder.accessLogWriter(AccessLogWriter.combined(), false);
            builder.annotatedService(new ApiService());
            builder.annotatedService(new SseService());
        };
    }
}
