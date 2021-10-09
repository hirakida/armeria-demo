package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.server.annotation.JacksonRequestConverterFunction;
import com.linecorp.armeria.server.annotation.JacksonResponseConverterFunction;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaServerConfig {
    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(HelloService helloService,
                                                               JacksonRequestConverterFunction jacksonRequestConverter,
                                                               JacksonResponseConverterFunction jacksonResponseConverter) {
        return builder -> builder.annotatedService(helloService,
                                                   jacksonRequestConverter,
                                                   jacksonResponseConverter)
                                 .accessLogWriter(AccessLogWriter.combined(), false);
    }

    @Bean
    public JacksonRequestConverterFunction jacksonRequestConverter(ObjectMapper objectMapper) {
        return new JacksonRequestConverterFunction(objectMapper);
    }

    @Bean
    public JacksonResponseConverterFunction jacksonResponseConverter(ObjectMapper objectMapper) {
        return new JacksonResponseConverterFunction(objectMapper);
    }
}
