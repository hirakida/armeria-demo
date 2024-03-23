package com.example;

import com.linecorp.armeria.common.HttpHeaderNames;
import com.linecorp.armeria.common.HttpHeaders;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.auth.AuthService;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;

public final class Main {
    public static void main(String[] args) {
        final Server server =
                Server.builder()
                      .http(8080)
                      .accessLogWriter(AccessLogWriter.combined(), false)
                      .serviceUnder("/docs",
                                    DocService.builder()
                                              .exampleHeaders(HelloService.class,
                                                              HttpHeaders.of(HttpHeaderNames.AUTHORIZATION,
                                                                             "Bearer TOKEN"))
                                              .build())
                      .decorator(LoggingService.newDecorator())
                      .annotatedService()
                      .decorator(AuthService.newDecorator(new AuthorizerImpl()))
                      .build(new HelloService())
                      .build();
        server.start().join();
    }
}
