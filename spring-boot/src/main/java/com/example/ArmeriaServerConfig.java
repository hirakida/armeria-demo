package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.common.metric.PrometheusMeterRegistries;
import com.linecorp.armeria.server.annotation.JacksonRequestConverterFunction;
import com.linecorp.armeria.server.annotation.JacksonResponseConverterFunction;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class ArmeriaServerConfig {
    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(HelloService helloService,
                                                               ObjectMapper objectMapper) {
        return builder -> builder.annotatedService(helloService,
                                                   new JacksonRequestConverterFunction(objectMapper),
                                                   new JacksonResponseConverterFunction(objectMapper))
                                 .accessLogWriter(AccessLogWriter.combined(), false);
    }

    @Bean
    public MeterRegistry meterRegistry() {
        return PrometheusMeterRegistries.defaultRegistry();
    }
}
