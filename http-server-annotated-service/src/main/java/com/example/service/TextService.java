package com.example.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import com.linecorp.armeria.common.HttpHeaderNames;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.RequestContext;
import com.linecorp.armeria.common.ResponseHeaders;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Description;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.HttpResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextService {

    @Get("/hello1")
    @Description("Hello1")
    public String hello1(ServiceRequestContext ctx) {
        log.info("{}", ctx.request());
        return "Hello!";
    }

    @Get("/hello2")
    @Description("Hello2")
    public HttpResult<String> hello2() {
        ResponseHeaders headers = ResponseHeaders.builder()
                                                 .status(HttpStatus.OK)
                                                 .add(HttpHeaderNames.CACHE_CONTROL, "no-cache")
                                                 .build();
        return HttpResult.of(headers, "Hello!!");
    }

    @Get("/future")
    public CompletableFuture<String> future(ServiceRequestContext ctx) {
        log.info("{}", ctx.request());
        ExecutorService executor = ctx.eventLoop();
        return CompletableFuture.supplyAsync(() -> {
            log.info("{}", RequestContext.current().request());
            return "Hello!";
        }, executor);
    }
}
