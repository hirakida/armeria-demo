package com.example;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.throttling.ThrottlingService;
import com.linecorp.armeria.server.throttling.ThrottlingStrategy;

public final class Main {
    public static void main(String[] args) {
        final Server server = Server.builder()
                                    .http(8080)
                                    .decorator(LoggingService.newDecorator())
                                    .accessLogWriter(AccessLogWriter.combined(), false)
                                    .service("/hello1", (ctx, req) -> HttpResponse.of("Hello1"))
                                    .service("/hello2", (ctx, req) -> HttpResponse.of("Hello2"))
                                    .routeDecorator()
                                    .path("/hello1")
                                    .build(ThrottlingService.newDecorator(ThrottlingStrategy.rateLimiting(1.0)))
                                    .build();
        server.start().join();
    }
}
