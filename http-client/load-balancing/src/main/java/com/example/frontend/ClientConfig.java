package com.example.frontend;

import static com.example.backend.BackendServerConfig.ENDPOINT_PORTS;
import static com.example.backend.BackendService.HEALTH_CHECK_PATH;
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

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ClientConfig {
    @Bean
    public HealthCheckedEndpointGroup healthCheckedEndpointGroup() {
        List<Endpoint> endpoints = ENDPOINT_PORTS.stream()
                                                 .map(port -> Endpoint.of("localhost", port))
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
