package com.example.service;

import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Get;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomDecoratingService {

    @Get("/decorator")
    public String decorator(ServiceRequestContext ctx) {
        log.info("{}", DateTimeDecoratingService.getDateTime(ctx));
        return "Hello!";
    }
}
