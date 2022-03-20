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
                              .decorator(delegate -> new HelloDecorator(delegate, "Hello5"))
                              .decorator(delegate -> new HelloDecorator(delegate, "Hello4"))
                              .build(new HelloService())
                              .decorator(delegate -> new HelloDecorator(delegate, "Hello3"))
                              .decorator(delegate -> new HelloDecorator(delegate, "Hello2"))
                              .decorator(delegate -> new HelloDecorator(delegate, "Hello1"))
                              .decorator("/hello",
                                         delegate -> new HelloDecorator(delegate, "router decorator2"))
                              .decorator("/hello",
                                         delegate -> new HelloDecorator(delegate, "router decorator1"))
                              .build();
        server.start().join();
    }
}
