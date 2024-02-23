package com.example;

import java.io.UncheckedIOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.HelloService.HelloRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.common.metric.PrometheusMeterRegistries;
import com.linecorp.armeria.server.annotation.JacksonRequestConverterFunction;
import com.linecorp.armeria.server.annotation.JacksonResponseConverterFunction;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import com.linecorp.armeria.spring.DocServiceConfigurator;

import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class ArmeriaServerConfig {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(HelloService helloService) {
        return builder -> builder.annotatedService(helloService,
                                                   new JacksonRequestConverterFunction(OBJECT_MAPPER),
                                                   new JacksonResponseConverterFunction(OBJECT_MAPPER))
                                 .decorator(LoggingService.newDecorator())
                                 .accessLogWriter(AccessLogWriter.combined(), false);
    }

    @Bean
    public DocServiceConfigurator docServiceConfigurator() {
        return builder -> builder.exampleQueries(HelloService.class, "hello1", "name=hirakida")
                                 .exampleQueries(HelloService.class, "hello2", "number=1")
                                 .exampleRequests(HelloService.class, "hello3",
                                                  toJsonString(new HelloRequest("Hello!")));
    }

    @Bean
    public MeterRegistry meterRegistry() {
        return PrometheusMeterRegistries.defaultRegistry();
    }

    private static String toJsonString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
}
