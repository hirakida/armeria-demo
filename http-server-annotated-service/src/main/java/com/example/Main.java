package com.example;

import com.example.service.BinaryService;
import com.example.service.BlockingService;
import com.example.service.FutureService;
import com.example.service.JsonService;
import com.example.service.TextService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.annotation.JacksonResponseConverterFunction;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;

public final class Main {
    public static void main(String[] args) {
        final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        final Server server = Server.builder()
                                    .http(8080)
                                    .decorator(LoggingService.newDecorator())
                                    .accessLogWriter(AccessLogWriter.combined(), false)
                                    .serviceUnder("/docs", new DocService())
                                    .annotatedService(new BinaryService())
                                    .annotatedService(new BlockingService())
                                    .annotatedService(new FutureService())
                                    .annotatedService(new TextService())
                                    .annotatedService(new JsonService(),
                                                      new JacksonResponseConverterFunction(objectMapper))
                                    .build();
        server.start().join();
    }
}
