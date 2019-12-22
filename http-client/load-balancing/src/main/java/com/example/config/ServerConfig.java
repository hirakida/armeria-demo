package com.example.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.BackendService;
import com.example.FrontendService;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ServerConfig {
    private static final List<Integer> PORTS = List.of(8081, 8082, 8083);

    public ServerConfig() {
        PORTS.forEach(port -> createServer(port).start().join());
    }

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(FrontendService frontendService) {
        return builder -> builder.annotatedService(frontendService);
    }

    private static Server createServer(int port) {
        return Server.builder()
                     .http(port)
                     .accessLogWriter(AccessLogWriter.combined(), false)
                     .annotatedService(new BackendService(port))
                     .build();
    }
}
