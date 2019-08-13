package com.example;

import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.logging.LoggingService;

public class Application {

    public static void main(String[] args) {
        Server server = new ServerBuilder()
                .http(8080)
                .service("/",
                         (ctx, req) -> HttpResponse.of("Hello, Armeria!"))
                .service("/delayed",
                         (ctx, req) -> HttpResponse.delayed(HttpResponse.of("delayed 3 seconds"),
                                                            Duration.ofSeconds(3)))
                .service("/date",
                         (ctx, req) -> HttpResponse.of(HttpStatus.OK,
                                                       MediaType.JSON_UTF_8,
                                                       "{\"date\":\"%s\"}",
                                                       LocalDate.now()))
                .decorator(LoggingService.newDecorator())
                .build();
        CompletableFuture<Void> future = server.start();
        future.join();
    }
}
