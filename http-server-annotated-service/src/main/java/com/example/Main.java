package com.example;

import com.example.service.BinaryService;
import com.example.service.TextService;
import com.example.service.JsonService;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;

public class Main {

    public static void main(String[] args) {
        Server server = Server.builder()
                              .http(8080)
                              .decorator(LoggingService.newDecorator())
                              .accessLogWriter(AccessLogWriter.combined(), false)
                              .annotatedService(new BinaryService())
                              .annotatedService(new TextService())
                              .annotatedService(new JsonService())
                              .build();
        server.start().join();
    }
}
