package com.example.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.util.DigestUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.server.annotation.ExceptionHandler;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;

@ExceptionHandler(ApiExceptionHandler.class)
public class ApiService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageDigest md5;

    public ApiService() {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Get("/date")
    public JsonNode date() {
        return toJsonNode(Map.of("date", LocalDate.now()));
    }

    @Get("/datetime")
    public JsonNode datetime() {
        return toJsonNode(Map.of("datetime", LocalDateTime.now()));
    }

    @Get("/md5/{text}")
    public String md5(@Param String text) {
        byte[] bytes = md5.digest(text.getBytes(StandardCharsets.UTF_8));
        return DigestUtils.md5DigestAsHex(bytes);
    }

    private JsonNode toJsonNode(Object object) {
        return objectMapper.convertValue(object, JsonNode.class);
    }
}
