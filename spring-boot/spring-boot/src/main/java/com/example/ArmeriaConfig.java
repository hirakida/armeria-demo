package com.example;

import java.io.UncheckedIOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.HelloService.HelloRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.logging.LogWriter;
import com.linecorp.armeria.common.prometheus.PrometheusMeterRegistries;
import com.linecorp.armeria.server.annotation.JacksonRequestConverterFunction;
import com.linecorp.armeria.server.annotation.JacksonResponseConverterFunction;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import com.linecorp.armeria.spring.DocServiceConfigurator;

import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;

@Configuration
public class ArmeriaConfig {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(HelloService helloService,
                                                               GitHubService gitHubService) {
        final JacksonRequestConverterFunction requestConverter =
                new JacksonRequestConverterFunction(OBJECT_MAPPER);
        final JacksonResponseConverterFunction responseConverter =
                new JacksonResponseConverterFunction(OBJECT_MAPPER);
        return builder -> builder.annotatedService(helloService, requestConverter, responseConverter)
                                 .annotatedService(gitHubService, requestConverter, responseConverter)
                                 .decorator(LoggingService.newDecorator())
                                 .accessLogWriter(AccessLogWriter.combined(), false);
    }

    @Bean
    public DocServiceConfigurator docServiceConfigurator() {
        return builder -> builder.exampleQueries(HelloService.class, "hello1", "name=Armeria")
                                 .exampleQueries(HelloService.class, "hello2", "number=1")
                                 .exampleRequests(HelloService.class, "hello3",
                                                  toJsonString(new HelloRequest("Hello!")))
                                 .examplePaths(GitHubService.class, "getUser", "/users/hirakida");
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder("https://api.github.com")
                         .decorator(LoggingClient.builder()
                                                 .logWriter(LogWriter.builder()
                                                                     .requestLogLevel(LogLevel.INFO)
                                                                     .successfulResponseLogLevel(LogLevel.INFO)
                                                                     .build())
                                                 .newDecorator())
                         .build();
    }

    @Bean
    public PrometheusMeterRegistry meterRegistry() {
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
