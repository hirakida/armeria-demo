package com.example;

import com.example.decorator.DateTimeDecorator;
import com.example.decorator.HelloDecorator;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;

public class Main {

    public static void main(String[] args) {
        Server server = Server.builder()
                              .http(8080)
                              .decorator(LoggingService.newDecorator())
                              .accessLogWriter(AccessLogWriter.combined(), false)
                              .serviceUnder("/docs", new DocService())
                              .annotatedService()
                              .decorator(DateTimeDecorator::new)
                              .decorator(service -> service.decorate(new HelloDecorator("Hello5")))
                              .decorator(service -> service.decorate(new HelloDecorator("Hello4")))
                              .build(new HelloService())
                              .decorator(new HelloDecorator("Hello3"))
                              .decorator(new HelloDecorator("Hello2"))
                              .decorator(new HelloDecorator("Hello1"))
                              .decorator("/hello", new HelloDecorator("router decorator2"))
                              .decorator("/hello", new HelloDecorator("router decorator1"))
                              .build();
        server.start().join();
    }
}
