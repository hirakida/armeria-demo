package com.example.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.common.HttpHeaderNames;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.ResponseHeaders;
import com.linecorp.armeria.server.annotation.ExceptionHandler;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.HttpResult;

@ExceptionHandler(ApiExceptionHandler.class)
public class JsonNodeService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Get("/date")
    public JsonNode getDate() {
        return objectMapper.valueToTree(LocalDate.now());
    }

    @Get("/datetime")
    public HttpResult<JsonNode> getDatetime() {
        ResponseHeaders headers = ResponseHeaders.builder()
                                                 .status(HttpStatus.OK)
                                                 .add(HttpHeaderNames.CACHE_CONTROL, "no-cache")
                                                 .build();
        JsonNode jsonNode = objectMapper.valueToTree(LocalDateTime.now());
        return HttpResult.of(headers, jsonNode);
    }

    @Get("/future")
    public CompletableFuture<JsonNode> future() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            return objectMapper.valueToTree(Map.of("date", LocalDate.now(),
                                                   "datetime", LocalDateTime.now()));
        });
    }
}
