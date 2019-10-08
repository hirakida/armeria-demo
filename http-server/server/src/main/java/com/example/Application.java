package com.example;

import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;

public class Application {

    public static void main(String[] args) {
        Server server = Server.builder()
                              .http(8080)
                              .serviceUnder("/docs", new DocService())
                              .decorator(LoggingService.newDecorator())
                              .accessLogWriter(AccessLogWriter.combined(), false)
                              .service("/",
                                       (ctx, req) -> HttpResponse.of("Hello, Armeria!"))
                              .service("/delayed",
                                       (ctx, req) -> HttpResponse.delayed(HttpResponse.of("delayed 3 seconds"),
                                                                          Duration.ofSeconds(3)))
                              .service("/json",
                                       (ctx, req) -> HttpResponse.of(HttpStatus.OK,
                                                                     MediaType.JSON_UTF_8,
                                                                     "{\"date\":\"%s\"}",
                                                                     LocalDate.now()))
                              .build();
        CompletableFuture<Void> future = server.start();
        future.join();
    }
}
