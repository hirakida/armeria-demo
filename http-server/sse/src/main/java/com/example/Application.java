package com.example;

import java.util.concurrent.CompletableFuture;

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
                              .annotatedService(new SseService())
                              .build();
        CompletableFuture<Void> future = server.start();
        future.join();
    }
}
