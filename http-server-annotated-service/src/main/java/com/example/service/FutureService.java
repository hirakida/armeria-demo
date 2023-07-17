package com.example.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.ContextAwareEventLoop;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.RequestContext;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.PathPrefix;

@PathPrefix("/future")
public class FutureService {
    private static final Logger logger = LoggerFactory.getLogger(FutureService.class);

    @Get("/1")
    public CompletableFuture<String> future1(ServiceRequestContext ctx) {
        logger.info("{}", ctx.eventLoop().inEventLoop());
        return CompletableFuture.supplyAsync(() -> {
            logger.info("{}", ctx.eventLoop().inEventLoop());
            return "Hello!";
        });
    }

    @Get("/2")
    public CompletableFuture<HttpResponse> future2(ServiceRequestContext ctx) {
        logger.info("{}", ctx.eventLoop().inEventLoop());
        return CompletableFuture.supplyAsync(() -> {
            logger.info("{}", ctx.eventLoop().inEventLoop());
            return HttpResponse.of("Hello!!");
        });
    }

    @Get("/3")
    public CompletableFuture<String> future3(ServiceRequestContext ctx) {
        final ContextAwareEventLoop eventLoop = ctx.eventLoop();
        logger.info("{}", eventLoop.inEventLoop());
        return CompletableFuture.supplyAsync(() -> {
            logger.info("{} {}", eventLoop.inEventLoop(), RequestContext.current().log());
            return "Hello!!!";
        }, eventLoop);
    }
}
