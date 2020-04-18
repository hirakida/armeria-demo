package com.example;

import java.time.Duration;
import java.time.LocalDateTime;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServiceRequestContext;
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
                              .service("/", (ctx, req) -> HttpResponse.of("Hello, Armeria!"))
                              .service("/json", Main::json)
                              .service("/delayed", Main::delayed)
                              .build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop().join()));

        server.start().join();
    }

    private static HttpResponse json(ServiceRequestContext ctx, HttpRequest req) {
        return HttpResponse.of(MediaType.JSON_UTF_8, "{\"datetime\":\"%s\"}", LocalDateTime.now());
    }

    private static HttpResponse delayed(ServiceRequestContext ctx, HttpRequest req) {
        HttpResponse response = HttpResponse.of("delayed");
        return HttpResponse.delayed(response, Duration.ofSeconds(3));
    }
}
