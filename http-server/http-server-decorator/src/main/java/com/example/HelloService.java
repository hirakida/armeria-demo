package com.example;

import static com.example.decorator.DateTimeDecorator.DATETIME_ATTR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.decorator.Class1Decorator;
import com.example.decorator.Class2Decorator;
import com.example.decorator.MethodDecorator;

import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Decorator;
import com.linecorp.armeria.server.annotation.Get;

@Decorator(Class1Decorator.class)
@Decorator(Class2Decorator.class)
public class HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);

    @Get("/hello")
    @Decorator(MethodDecorator.class)
    public String hello(ServiceRequestContext ctx) {
        logger.info("DATETIME_ATTR={}", ctx.attr(DATETIME_ATTR));
        ctx.attrs().forEachRemaining(attr -> logger.info("attr={}", attr));
        return "Hello!";
    }
}
