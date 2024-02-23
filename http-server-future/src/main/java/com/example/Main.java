package com.example;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.logging.LoggingService;

public final class Main {
    public static void main(String[] args) {
        final Server server = Server.builder()
                                    .http(8080)
                                    .decorator(LoggingService.newDecorator())
                                    .annotatedService(new FutureService())
                                    .build();
        server.start().join();
    }
}
