package com.example;

import java.util.concurrent.CompletableFuture;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.throttling.RateLimitingThrottlingStrategy;
import com.linecorp.armeria.server.throttling.ThrottlingHttpService;

public class Application {

    public static void main(String[] args) {
        Server server = Server.builder()
                              .http(8080)
                              .serviceUnder("/docs", new DocService())
                              .decorator(LoggingService.newDecorator())
                              .decorator(ThrottlingHttpService.newDecorator(
                                      new RateLimitingThrottlingStrategy<>(1.0)))
                              .service("/", (ctx, req) -> HttpResponse.of(HttpStatus.OK))
                              .build();
        CompletableFuture<Void> future = server.start();
        future.join();
    }
}
