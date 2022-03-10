package com.example;

import static com.example.decorator.DateTimeDecorator.DATETIME_ATTR;

import com.example.decorator.Class1Decorator;
import com.example.decorator.Class2Decorator;
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
        log.info("DATETIME_ATTR={}", ctx.attr(DATETIME_ATTR));
        ctx.attrs().forEachRemaining(attr -> log.info("attr={}", attr));
        return "Hello!";
    }
}
