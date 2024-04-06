package com.example;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Get;

import io.netty.util.AttributeKey;

public class HelloService {
    private static final AttributeKey<LocalTime> TIME_ATTR =
            AttributeKey.valueOf(HelloService.class, "TIME_ATTR");
    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);

    @Get("/hello")
    public String hello(ServiceRequestContext ctx) {
        ctx.setAttr(TIME_ATTR, LocalTime.now());
        logger.info("{}", ctx.attr(TIME_ATTR));
        return "Hello!";
    }
}
