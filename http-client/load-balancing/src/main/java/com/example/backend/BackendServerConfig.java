package com.example.backend;

import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.logging.AccessLogWriter;

@Configuration
public class BackendServerConfig {
    public static final List<Integer> ENDPOINT_PORTS = List.of(8081, 8082, 8083);

    public BackendServerConfig() {
        ENDPOINT_PORTS.forEach(port -> createServer(port).start().join());
    }

    private static Server createServer(int port) {
        return Server.builder()
                     .http(port)
                     .accessLogWriter(AccessLogWriter.combined(), false)
                     .annotatedService(new BackendService(port))
                     .build();
    }
}
