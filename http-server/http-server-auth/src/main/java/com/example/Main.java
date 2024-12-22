package com.example;

import static com.linecorp.armeria.common.HttpHeaderNames.AUTHORIZATION;

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
                      .decorator(LoggingService.newDecorator())
                      .serviceUnder("/docs",
                                    DocService.builder()
                                              .exampleHeaders(Auth1Service.class,
                                                              HttpHeaders.of(AUTHORIZATION, "Bearer TOKEN1"))
                                              .exampleHeaders(Auth2Service.class,
                                                              HttpHeaders.of(AUTHORIZATION, "Bearer TOKEN2"))
                                              .build())
                      .annotatedService(new Auth1Service(),
                                        AuthService.newDecorator(new AuthorizerImpl()))
                      .annotatedService(new Auth2Service(),
                                        AuthService.newDecorator(new AuthorizerWithHandlers()))
                      .build();
        server.start().join();
    }
}
