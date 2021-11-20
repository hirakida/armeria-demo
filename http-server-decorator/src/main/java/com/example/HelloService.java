package com.example;

import com.example.decorator.Class1Decorator;
import com.example.decorator.Class2Decorator;
import com.example.decorator.DateTimeDecoratingService;
import com.example.decorator.MethodDecorator;

import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Decorator;
import com.linecorp.armeria.server.annotation.Get;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Decorator(Class1Decorator.class)
@Decorator(Class2Decorator.class)
public class HelloService {

    @Get("/hello")
    @Decorator(MethodDecorator.class)
    public String hello(ServiceRequestContext ctx) {
        log.info("{}", DateTimeDecoratingService.getDateTime(ctx));
        return "Hello!";
    }
}
