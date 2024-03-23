package com.example;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.linecorp.armeria.client.Endpoint;
import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.endpoint.EndpointGroup;
import com.linecorp.armeria.client.endpoint.healthcheck.HealthCheckedEndpointGroup;
import com.linecorp.armeria.common.SessionProtocol;

@Configuration
public class ArmeriaConfig {
    @Bean
    @DependsOn("serverConfig")
    public HealthCheckedEndpointGroup healthCheckedEndpointGroup() {
        final EndpointGroup endpointGroup = EndpointGroup.of(Endpoint.of("localhost", 8081),
                                                             Endpoint.of("localhost", 8082),
                                                             Endpoint.of("localhost", 8083));
        final HealthCheckedEndpointGroup healthCheckedEndpointGroup =
                HealthCheckedEndpointGroup.builder(endpointGroup, "/internal/l7check/")
                                          .protocol(SessionProtocol.HTTP)
                                          .retryInterval(Duration.ofSeconds(1))
                                          .build();
        try {
            healthCheckedEndpointGroup.whenReady().get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        return healthCheckedEndpointGroup;
    }

    @Bean
    public RestClient restClient(HealthCheckedEndpointGroup healthCheckedEndpointGroup) {
        return RestClient.of(SessionProtocol.HTTP, healthCheckedEndpointGroup);
    }
}
