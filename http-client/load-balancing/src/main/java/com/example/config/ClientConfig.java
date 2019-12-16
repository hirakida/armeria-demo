package com.example.config;

import static com.example.BackendService.HEALTH_CHECK_PATH;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.Endpoint;
import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.endpoint.EndpointGroup;
import com.linecorp.armeria.client.endpoint.EndpointGroupRegistry;
import com.linecorp.armeria.client.endpoint.EndpointSelectionStrategy;
import com.linecorp.armeria.client.endpoint.healthcheck.HealthCheckedEndpointGroup;
import com.linecorp.armeria.common.SessionProtocol;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ClientConfig {
    private static final String GROUP_NAME = "my_servers";

    @Bean
    public EndpointGroup endpointGroup() {
        return EndpointGroup.of(Endpoint.of("localhost", 8081),
                                Endpoint.of("localhost", 8082),
                                Endpoint.of("localhost", 8083));
    }

    @Bean
    public HealthCheckedEndpointGroup healthCheckedEndpointGroup(EndpointGroup endpointGroup) {
        return HealthCheckedEndpointGroup.builder(endpointGroup, HEALTH_CHECK_PATH)
                                         .protocol(SessionProtocol.HTTP)
                                         .retryInterval(Duration.ofSeconds(5))
                                         .build();
    }

    @Bean
    public WebClient webClient(HealthCheckedEndpointGroup healthCheckedGroup) {
        registerEndpointGroup(healthCheckedGroup);
        return WebClient.builder("http://group:" + GROUP_NAME)
                        .build();
    }

    private static void registerEndpointGroup(HealthCheckedEndpointGroup healthCheckedGroup) {
        try {
            healthCheckedGroup.awaitInitialEndpoints();
        } catch (InterruptedException e) {
            log.error("awaitInitialEndpoints:", e);
        }

        EndpointGroupRegistry.register(GROUP_NAME, healthCheckedGroup,
                                       EndpointSelectionStrategy.WEIGHTED_ROUND_ROBIN);
    }
}
