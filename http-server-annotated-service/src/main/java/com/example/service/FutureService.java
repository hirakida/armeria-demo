package com.example.service;

import java.util.concurrent.CompletableFuture;

import com.linecorp.armeria.common.ContextAwareEventLoop;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.RequestContext;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.PathPrefix;

import lombok.extern.slf4j.Slf4j;

@PathPrefix("/future")
@Slf4j
public class FutureService {
    @Get("/1")
    public CompletableFuture<String> future1(ServiceRequestContext ctx) {
        log.info("{}", ctx.eventLoop().inEventLoop());
        return CompletableFuture.supplyAsync(() -> {
            log.info("{}", ctx.eventLoop().inEventLoop());
            return "Hello!";
        });
    }

    @Get("/2")
    public CompletableFuture<HttpResponse> future2(ServiceRequestContext ctx) {
        log.info("{}", ctx.eventLoop().inEventLoop());
        return CompletableFuture.supplyAsync(() -> {
            log.info("{}", ctx.eventLoop().inEventLoop());
            return HttpResponse.of("Hello!!");
        });
    }

    @Get("/3")
    public CompletableFuture<String> future3(ServiceRequestContext ctx) {
        final ContextAwareEventLoop eventLoop = ctx.eventLoop();
        log.info("{}", eventLoop.inEventLoop());
        return CompletableFuture.supplyAsync(() -> {
            log.info("{} {}", eventLoop.inEventLoop(), RequestContext.current().log());
            return "Hello!!!";
        }, eventLoop);
    }
}
