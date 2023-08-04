package com.example;

import com.example.decorator.AuthorizerImpl;
import com.example.decorator.DateTimeDecorator;
import com.example.decorator.HelloDecorator;

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
                                              .exampleHeaders(MyAuthService.class,
                                                              HttpHeaders.of(HttpHeaderNames.AUTHORIZATION,
                                                                             "Bearer TOKEN"))
                                              .build())
                      .decorator(LoggingService.newDecorator())
                      .decorator(delegate -> new HelloDecorator(delegate, "Hello0"))
                      .decorator("/hello",
                                 delegate -> new HelloDecorator(delegate, "router decorator2"))
                      .decorator("/hello",
                                 delegate -> new HelloDecorator(delegate, "router decorator1"))
                      // HelloService
                      .annotatedService()
                      .decorator(DateTimeDecorator::new)
                      .decorator(delegate -> new HelloDecorator(delegate, "Hello2"))
                      .decorator(delegate -> new HelloDecorator(delegate, "Hello1"))
                      .build(new HelloService())
                      // MyAuthService
                      .annotatedService()
                      .decorator(AuthService.newDecorator(new AuthorizerImpl()))
                      .build(new MyAuthService())
                      .build();
        server.start().join();
    }
}
