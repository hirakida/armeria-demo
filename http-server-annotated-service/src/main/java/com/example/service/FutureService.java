package com.example.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.ContextAwareEventLoop;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.PathPrefix;

@PathPrefix("/future")
public class FutureService {
    private static final Logger logger = LoggerFactory.getLogger(FutureService.class);

    @Get("/1")
    public CompletableFuture<String> future1(ServiceRequestContext ctx) {
        logger.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
        return CompletableFuture.supplyAsync(() -> {
            logger.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
            return "Hello!";
        });
    }

    @Get("/2")
    public CompletableFuture<Void> future2(ServiceRequestContext ctx) {
        logger.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
        return CompletableFuture.runAsync(() -> {
            logger.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
        });
    }

    @Get("/3")
    public CompletableFuture<Void> future3(ServiceRequestContext ctx) {
        final ContextAwareEventLoop eventLoop = ctx.eventLoop();
        logger.info("inEventLoop={}", eventLoop.inEventLoop());
        return CompletableFuture.runAsync(() -> {
            logger.info("inEventLoop={}", eventLoop.inEventLoop());
        }, eventLoop);
    }
}
