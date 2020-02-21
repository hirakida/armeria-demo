package com.example.backend;

import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.Endpoint;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.logging.AccessLogWriter;

@Configuration
public class BackendServerConfig {
    public static final List<Endpoint> ENDPOINTS = List.of(Endpoint.of("localhost", 8081),
                                                           Endpoint.of("localhost", 8082),
                                                           Endpoint.of("localhost", 8083));

    public BackendServerConfig() {
        ENDPOINTS.forEach(endpoint -> createServer(endpoint.port()).start().join());
    }

    private static Server createServer(int port) {
        return Server.builder()
                     .http(port)
                     .accessLogWriter(AccessLogWriter.combined(), false)
                     .annotatedService(new BackendService(port))
                     .build();
    }
}
