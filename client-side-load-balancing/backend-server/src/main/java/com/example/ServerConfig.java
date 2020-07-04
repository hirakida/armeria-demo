package com.example;

import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.logging.AccessLogWriter;

@Configuration
public class ServerConfig {
    private static final List<Integer> PORTS = List.of(8081, 8082, 8083);

    public ServerConfig() {
        PORTS.forEach(port -> createServer(port).start().join());
    }

    private static Server createServer(int port) {
        return Server.builder()
                     .http(port)
                     .accessLogWriter(AccessLogWriter.combined(), false)
                     .annotatedService(new BackendService(port))
                     .annotatedService(new HealthCheckService())
                     .build();
    }
}
