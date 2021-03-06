package com.example;

import com.example.service.BinaryService;
import com.example.service.BlockingService;
import com.example.service.JsonService;
import com.example.service.TextService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.annotation.JacksonResponseConverterFunction;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;

public class Main {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        JacksonResponseConverterFunction responseConverter = new JacksonResponseConverterFunction(objectMapper);

        Server server = Server.builder()
                              .http(8080)
                              .decorator(LoggingService.newDecorator())
                              .accessLogWriter(AccessLogWriter.combined(), false)
                              .annotatedService(new BinaryService())
                              .annotatedService(new BlockingService())
                              .annotatedService(new TextService())
                              .annotatedService(new JsonService(), responseConverter)
                              .build();
        server.start().join();
    }
}
