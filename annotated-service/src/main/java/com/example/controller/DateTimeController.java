package com.example.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.server.annotation.Get;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DateTimeController {
    private final ObjectMapper objectMapper;

    @Get("/date")
    public JsonNode date() {
        return toJsonNode(Map.of("date", LocalDate.now()));
    }

    @Get("/datetime")
    public JsonNode datetime() {
        return toJsonNode(Map.of("datetime", LocalDateTime.now()));
    }

    private JsonNode toJsonNode(Object object) {
        return objectMapper.convertValue(object, JsonNode.class);
    }
}
