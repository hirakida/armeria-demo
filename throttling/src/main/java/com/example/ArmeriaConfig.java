package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.throttling.RateLimitingThrottlingStrategy;
import com.linecorp.armeria.server.throttling.ThrottlingHttpService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(ApiService apiService) {
        return builder -> {
            builder.decorator(LoggingService.newDecorator());
            builder.decorator(ThrottlingHttpService.newDecorator(new RateLimitingThrottlingStrategy<>(1.0)));
            builder.annotatedService(apiService);
        };
    }
}
