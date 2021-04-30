package com.example;

import java.time.Duration;

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
    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(FrontendService frontendService) {
        return builder -> builder.annotatedService(frontendService);
    }

    @Bean
    public HealthCheckedEndpointGroup healthCheckedEndpointGroup() {
        EndpointGroup endpointGroup = EndpointGroup.of(Endpoint.of("localhost", 8081),
                                                       Endpoint.of("localhost", 8082),
                                                       Endpoint.of("localhost", 8083));
        return HealthCheckedEndpointGroup.builder(endpointGroup, "/internal/l7check/")
                                         .protocol(SessionProtocol.HTTP)
                                         .retryInterval(Duration.ofSeconds(5))
                                         .build();
    }

    @Bean
    public WebClient webClient(HealthCheckedEndpointGroup healthCheckedEndpointGroup) {
        return WebClient.of(SessionProtocol.HTTP, healthCheckedEndpointGroup);
    }
}
