package com.example.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.linecorp.armeria.common.HttpData;
import com.linecorp.armeria.common.HttpHeaderNames;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.ResponseHeaders;
import com.linecorp.armeria.server.annotation.ExceptionHandler;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.HttpResult;
import com.linecorp.armeria.server.annotation.ProducesBinary;
import com.linecorp.armeria.server.annotation.ProducesJson;

@ExceptionHandler(ApiExceptionHandler.class)
public class DemoService {

    @Get("/text")
    public String text() {
        return "Hello, Armeria!";
    }

    @Get("/binary")
    @ProducesBinary
    public HttpData binary() {
        return HttpData.of(StandardCharsets.UTF_8, "hello!");
    }

    @Get("/date")
    @ProducesJson
    public LocalDate getDate() {
        return LocalDate.now();
    }

    @Get("/datetime")
    @ProducesJson
    public HttpResult<LocalDateTime> getDatetime() {
        ResponseHeaders headers = ResponseHeaders.builder()
                                                 .status(HttpStatus.OK)
                                                 .add(HttpHeaderNames.CACHE_CONTROL, "no-cache")
                                                 .build();
        return HttpResult.of(headers, LocalDateTime.now());
    }

    @Get("/future")
    @ProducesJson
    public CompletableFuture<Map<String, ?>> future() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            return Map.of("date", LocalDate.now(),
                          "datetime", LocalDateTime.now());
        });
    }
}
