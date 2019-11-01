package com.example;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.throttling.RateLimitingThrottlingStrategy;
import com.linecorp.armeria.server.throttling.ThrottlingHttpService;

public class Application {

    public static void main(String[] args) {
        Server server = Server.builder()
                              .http(8080)
                              .serviceUnder("/docs", new DocService())
                              .decorator(LoggingService.newDecorator())
                              .accessLogWriter(AccessLogWriter.combined(), false)
                              .service("/", (ctx, req) -> HttpResponse.of("Hello, Armeria!"))
                              .service("/delayed", new DelayedService())
                              .service("/json", new JsonService()
                                      .decorate(ThrottlingHttpService.newDecorator(
                                              new RateLimitingThrottlingStrategy<>(1.0))))
                              .build();
        server.start().join();
    }

    private static class DelayedService implements HttpService {
        @Override
        public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
            return HttpResponse.delayed(HttpResponse.of("delayed 3 seconds"),
                                        Duration.ofSeconds(3));
        }
    }

    private static class JsonService implements HttpService {
        @Override
        public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
            return HttpResponse.of(HttpStatus.OK,
                                   MediaType.JSON_UTF_8,
                                   "{\"date\":\"%s\",\"datetime\":\"%s\"}",
                                   LocalDate.now(), LocalDateTime.now());
        }
    }
}
