package com.example;

import static com.example.AuthorizerWithHandlers.TEST_HEADER;

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
                      .decorator(LoggingService.newDecorator())
                      .serviceUnder("/docs",
                                    DocService.builder()
                                              .exampleHeaders(Auth1Service.class,
                                                              HttpHeaders.of(HttpHeaderNames.AUTHORIZATION,
                                                                             "Bearer TOKEN"))
                                              .exampleHeaders(Auth2Service.class,
                                                              HttpHeaders.of(TEST_HEADER,
                                                                             "test"))
                                              .build())
                      .annotatedService(new Auth1Service(),
                                        AuthService.newDecorator(new AuthorizerImpl()))
                      .annotatedService()
                      .decorator(AuthService.newDecorator(new AuthorizerWithHandlers()))
                      .build(new Auth2Service())
                      .build();
        server.start().join();
    }
}
