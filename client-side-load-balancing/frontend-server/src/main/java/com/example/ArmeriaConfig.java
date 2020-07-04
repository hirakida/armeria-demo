package com.example;

import static java.util.stream.Collectors.toList;

import java.time.Duration;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.Endpoint;
import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.endpoint.EndpointGroup;
import com.linecorp.armeria.client.endpoint.healthcheck.HealthCheckedEndpointGroup;
import com.linecorp.armeria.common.SessionProtocol;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaConfig {
    private static final List<String> ENDPOINTS = List.of("localhost:8081",
                                                          "localhost:8082",
                                                          "localhost:8083");
    private static final String HEALTH_CHECK_PATH = "/internal/l7check/";

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(FrontendService frontendService) {
        return builder -> builder.annotatedService(frontendService);
    }

    @Bean
    public HealthCheckedEndpointGroup healthCheckedEndpointGroup() {
        List<Endpoint> endpoints = ENDPOINTS.stream()
                                            .map(Endpoint::parse)
                                            .collect(toList());
        EndpointGroup endpointGroup = EndpointGroup.of(endpoints);
        return HealthCheckedEndpointGroup.builder(endpointGroup, HEALTH_CHECK_PATH)
                                         .protocol(SessionProtocol.HTTP)
                                         .retryInterval(Duration.ofSeconds(5))
                                         .build();
    }

    @Bean
    public WebClient webClient(HealthCheckedEndpointGroup healthCheckedEndpointGroup) {
        return WebClient.of(SessionProtocol.HTTP, healthCheckedEndpointGroup);
    }
}
