package com.example.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.server.annotation.Get;

public class DateTimeService {
    private final ObjectMapper objectMapper = new ObjectMapper();

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
