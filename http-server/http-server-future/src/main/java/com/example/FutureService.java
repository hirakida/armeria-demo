package com.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.ContextAwareEventLoop;
import com.linecorp.armeria.common.RequestContext;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Get;

public class FutureService {
    private static final Logger logger = LoggerFactory.getLogger(FutureService.class);
    private final ExecutorService executorService1 = Executors.newSingleThreadExecutor();
    private final ExecutorService executorService2 = RequestContext.makeContextPropagating(executorService1);

    @Get("/1")
    public CompletableFuture<String> future1(ServiceRequestContext ctx) {
        logger.info("inEventLoop={} {}", ctx.eventLoop().inEventLoop(), RequestContext.currentOrNull());

        return CompletableFuture
                .supplyAsync(() -> {
                    logger.info("inEventLoop={} {}", ctx.eventLoop().inEventLoop(),
                                RequestContext.currentOrNull());
                    return "Hello!";
                })
                .thenApplyAsync(value -> {
                    logger.info("inEventLoop={} {}", ctx.eventLoop().inEventLoop(),
                                RequestContext.currentOrNull());
                    return value;
                });
    }

    @Get("/2")
    public CompletableFuture<String> future2(ServiceRequestContext ctx) {
        logger.info("inEventLoop={} {}", ctx.eventLoop().inEventLoop(), RequestContext.currentOrNull());
        final ContextAwareEventLoop eventLoop = ctx.eventLoop();

        return CompletableFuture
                .supplyAsync(() -> {
                    logger.info("inEventLoop={} {}", ctx.eventLoop().inEventLoop(),
                                RequestContext.currentOrNull());
                    return "Hello!";
                }, eventLoop)
                .thenApplyAsync(value -> {
                    logger.info("inEventLoop={} {}", ctx.eventLoop().inEventLoop(),
                                RequestContext.currentOrNull());
                    return value;
                }, eventLoop);
    }

    @Get("/3")
    public CompletableFuture<String> future3(ServiceRequestContext ctx) {
        logger.info("inEventLoop={} {}", ctx.eventLoop().inEventLoop(), RequestContext.currentOrNull());

        ctx.makeContextAware(() -> logger.info("inEventLoop={} {}", ctx.eventLoop().inEventLoop(),
                                               RequestContext.currentOrNull()))
           .run();
        ctx.makeContextAware(executorService1)
           .execute(() -> logger.info("inEventLoop={} {}", ctx.eventLoop().inEventLoop(),
                                      RequestContext.currentOrNull()));

        return CompletableFuture
                .supplyAsync(() -> {
                    logger.info("inEventLoop={} {}", ctx.eventLoop().inEventLoop(),
                                RequestContext.currentOrNull());
                    return "Hello!";
                }, executorService1)
                .thenApplyAsync(value -> {
                    logger.info("inEventLoop={} {}", ctx.eventLoop().inEventLoop(),
                                RequestContext.currentOrNull());
                    return value;
                }, executorService2);
    }
}
