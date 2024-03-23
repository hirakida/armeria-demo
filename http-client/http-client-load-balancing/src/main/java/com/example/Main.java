package com.example;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import com.example.backend.BackendService;
import com.example.backend.HealthCheckService;

import com.linecorp.armeria.client.Endpoint;
import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.endpoint.EndpointGroup;
import com.linecorp.armeria.client.endpoint.healthcheck.HealthCheckedEndpointGroup;
import com.linecorp.armeria.common.SessionProtocol;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;

public final class Main {
    public static void main(String[] args) {
        Stream.of(10001, 10002, 10003)
              .map(Main::createBackendServer)
              .forEach(server -> server.start().join());

        final Server server =
                Server.builder()
                      .http(8080)
                      .serviceUnder("/docs", DocService.builder().build())
                      .decorator(LoggingService.newDecorator())
                      .annotatedService(new LoadBalancingService(createRestClient()))
                      .build();
        server.start().join();
    }

    private static Server createBackendServer(int port) {
        return Server.builder()
                     .http(port)
                     .serviceUnder("/docs", DocService.builder().build())
                     .accessLogWriter(AccessLogWriter.combined(), false)
                     .annotatedService(new BackendService(port))
                     .annotatedService(new HealthCheckService())
                     .build();
    }

    private static RestClient createRestClient() {
        final EndpointGroup endpointGroup = EndpointGroup.of(Endpoint.of("localhost", 10001),
                                                             Endpoint.of("localhost", 10002),
                                                             Endpoint.of("localhost", 10003));
        final HealthCheckedEndpointGroup healthCheckedEndpointGroup =
                HealthCheckedEndpointGroup.builder(endpointGroup, "/internal/l7check")
                                          .protocol(SessionProtocol.HTTP)
                                          .retryInterval(Duration.ofSeconds(3))
                                          .build();
        try {
            healthCheckedEndpointGroup.whenReady().get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }

        return RestClient.of(SessionProtocol.HTTP, healthCheckedEndpointGroup);
    }
}
