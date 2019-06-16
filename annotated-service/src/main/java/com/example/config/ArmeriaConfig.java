package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.controller.DateTimeController;
import com.example.controller.FizzBuzzController;
import com.example.controller.MessageDigestController;

import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(DateTimeController dateTimeController,
                                                               MessageDigestController messageDigestController,
                                                               FizzBuzzController fizzBuzzController) {
        return builder -> {
            builder.serviceUnder("/docs", new DocService());
            builder.decorator(LoggingService.newDecorator());
            builder.accessLogWriter(AccessLogWriter.combined(), false);
            builder.annotatedService(dateTimeController);
            builder.annotatedService(messageDigestController);
            builder.annotatedService(fizzBuzzController);
        };
    }
}
