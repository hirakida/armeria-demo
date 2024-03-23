package com.example;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.LoggingService;

public final class Main {
    public static void main(String[] args) {
        final Server server =
                Server.builder()
                      .http(8080)
                      .serviceUnder("/docs", new DocService())
                      .decorator(LoggingService.newDecorator())
                      .annotatedService(new StreamingService())
                      .build();
        server.start().join();
    }
}
