package com.example;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.throttling.ThrottlingService;
import com.linecorp.armeria.server.throttling.ThrottlingStrategy;

public class Main {

    public static void main(String[] args) {
        ServerBuilder builder = Server.builder()
                                      .http(8080)
                                      .serviceUnder("/docs", new DocService())
                                      .decorator(LoggingService.newDecorator())
                                      .accessLogWriter(AccessLogWriter.combined(), false);

        builder.annotatedService(new Hello1Service())
               .annotatedService(new Hello2Service())
               .routeDecorator()
               .path("/hello1")
               .build(ThrottlingService.newDecorator(ThrottlingStrategy.rateLimiting(1.0)));

        Server server = builder.build();
        server.start().join();
    }
}
