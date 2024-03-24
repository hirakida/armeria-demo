package com.example;

import org.jetbrains.annotations.VisibleForTesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.annotation.JacksonResponseConverterFunction;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;

public final class Main {
    public static void main(String[] args) {
        final ServerBuilder sb = Server.builder();
        configureServices(sb);
        final Server server = sb.build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop().join()));
        server.start().join();
    }

    @VisibleForTesting
    static void configureServices(ServerBuilder sb) {
        final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        sb.http(8080)
          .decorator(LoggingService.newDecorator())
          .accessLogWriter(AccessLogWriter.combined(), false)
          .serviceUnder("/docs", new DocService())
          .service("/", (ctx, req) -> HttpResponse.of("Hello, Armeria!"))
          .annotatedService(new HelloService())
          .annotatedService(new FutureService())
          .annotatedService(new BlockingService())
          .annotatedService(new StreamingService())
          .annotatedService(new JsonService(),
                            new JacksonResponseConverterFunction(objectMapper));
    }
}
