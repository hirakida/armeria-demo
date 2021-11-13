package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.common.metric.MeterIdPrefixFunction;
import com.linecorp.armeria.common.metric.PrometheusMeterRegistries;
import com.linecorp.armeria.server.annotation.JacksonRequestConverterFunction;
import com.linecorp.armeria.server.annotation.JacksonResponseConverterFunction;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

import io.micrometer.core.instrument.composite.CompositeMeterRegistry;

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
    public CompositeMeterRegistry meterRegistry() {
        CompositeMeterRegistry meterRegistry = new CompositeMeterRegistry();
        meterRegistry.add(PrometheusMeterRegistries.defaultRegistry());
        return meterRegistry;
    }

    //    @Bean
    public MeterIdPrefixFunction meterIdPrefixFunction() {
        return MeterIdPrefixFunction.ofDefault("armeria.server");
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
