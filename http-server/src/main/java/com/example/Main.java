package com.example;

import java.time.Duration;
import java.time.LocalDateTime;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;

public class Main {

    public static void main(String[] args) {
        Server server = Server.builder()
                              .http(8080)
                              .serviceUnder("/docs", new DocService())
                              .decorator(LoggingService.newDecorator())
                              .accessLogWriter(AccessLogWriter.combined(), false)
                              .service("/",
                                       (ctx, req) -> HttpResponse.of("Hello, Armeria!"))
                              .service("/json",
                                       (ctx, req) -> HttpResponse.of(MediaType.JSON_UTF_8,
                                                                     "{\"datetime\":\"%s\"}",
                                                                     LocalDateTime.now()))
                              .service("/delayed",
                                       (ctx, req) -> {
                                           HttpResponse response = HttpResponse.of("Hello!");
                                           return HttpResponse.delayed(response, Duration.ofSeconds(3));
                                       })
                              .build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop().join()));

        server.start().join();
    }
}
