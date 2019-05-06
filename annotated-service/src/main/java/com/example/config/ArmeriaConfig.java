package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.service.DateTimeService;
import com.example.service.FizzBuzzService;
import com.example.service.MessageDigestService;

import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(DateTimeService dateTimeService,
                                                               MessageDigestService messageDigestService,
                                                               FizzBuzzService fizzBuzzService) {
        return builder -> {
            builder.serviceUnder("/docs", new DocService());
            builder.decorator(LoggingService.newDecorator());
            builder.accessLogWriter(AccessLogWriter.combined(), false);
            builder.annotatedService(dateTimeService);
            builder.annotatedService(messageDigestService);
            builder.annotatedService(fizzBuzzService);
        };
    }
}
