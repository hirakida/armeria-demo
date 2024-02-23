package com.example;

import java.time.Duration;
import java.util.Map;

import org.jetbrains.annotations.VisibleForTesting;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.common.sse.ServerSentEvent;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.streaming.JsonLines;
import com.linecorp.armeria.server.streaming.JsonTextSequences;
import com.linecorp.armeria.server.streaming.ServerSentEvents;

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
        sb.http(8080)
          .decorator(LoggingService.newDecorator())
          .accessLogWriter(AccessLogWriter.combined(), false)
          .serviceUnder("/docs", new DocService())
          .service("/",
                   (ctx, req) -> HttpResponse.of("Hello, Armeria!"))
          .service("/json/:name",
                   (ctx, req) -> HttpResponse.of(MediaType.JSON_UTF_8,
                                                 "{\"message\":\"Hello, %s!\"}",
                                                 ctx.pathParam("name")))
          .service("/delayed",
                   (ctx, req) -> HttpResponse.delayed(HttpResponse.of("Hello!"),
                                                      Duration.ofSeconds(3)))
          .service("/x-ndjson",
                   (ctx, req) -> JsonLines.fromObject(Map.of("message", "Hello!")))
          .service("/json-seq",
                   (ctx, req) -> JsonTextSequences.fromObject(Map.of("message", "Hello!")))
          .service("/sse",
                   (ctx, req) -> ServerSentEvents.fromEvent(ServerSentEvent.ofEvent("Hello!")));
    }
}
