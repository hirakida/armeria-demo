package com.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.ContextAwareEventLoop;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.RequestContext;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Get;

public class FutureService {
    private static final Logger logger = LoggerFactory.getLogger(FutureService.class);
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final ExecutorService propagatingExecutor = RequestContext.makeContextPropagating(executor);

    @Get("/future1")
    public CompletableFuture<String> future1(ServiceRequestContext ctx) {
        final ContextAwareEventLoop eventLoop = ctx.eventLoop();
        // true, non-null
        logger.info("inEventLoop={} {}", eventLoop.inEventLoop(), RequestContext.currentOrNull());

        return CompletableFuture
                .supplyAsync(() -> {
                    // false, null
                    logger.info("inEventLoop={} {}", eventLoop.inEventLoop(), RequestContext.currentOrNull());
                    return "Hello!";
                })
                .thenApplyAsync(value -> {
                    // false, null
                    logger.info("inEventLoop={} {}", eventLoop.inEventLoop(), RequestContext.currentOrNull());
                    return value;
                }, executor)
                .thenApplyAsync(value -> {
                    // true, non-null
                    logger.info("inEventLoop={} {}", eventLoop.inEventLoop(), RequestContext.currentOrNull());
                    return value;
                }, eventLoop);
    }

    @Get("/future2")
    public CompletableFuture<String> future2(ServiceRequestContext ctx) {
        final ContextAwareEventLoop eventLoop = ctx.eventLoop();
        return CompletableFuture
                .supplyAsync(() -> {
                    // false, non-null
                    logger.info("inEventLoop={} {}", eventLoop.inEventLoop(), RequestContext.currentOrNull());
                    return "Hello!";
                }, propagatingExecutor)
                .thenApply(value -> {
                    // false, non-null
                    logger.info("inEventLoop={} {}", eventLoop.inEventLoop(), RequestContext.currentOrNull());
                    return value;
                });
    }

    @Get("/future3")
    public HttpResponse future3(ServiceRequestContext ctx) {
        final ContextAwareEventLoop eventLoop = ctx.eventLoop();
        // true, non-null
        ctx.makeContextAware(() -> logger.info("inEventLoop={} {}", eventLoop.inEventLoop(),
                                               RequestContext.currentOrNull()))
           .run();

        final CompletableFuture<HttpResponse> future = new CompletableFuture<>();
        ctx.makeContextAware(executor)
           .execute(() -> {
               // false, non-null
               logger.info("inEventLoop={} {}", eventLoop.inEventLoop(), RequestContext.currentOrNull());
               future.complete(HttpResponse.of("Hello!"));
           });
        return HttpResponse.of(future);
    }

    @Get("/future4")
    public CompletableFuture<String> future4(ServiceRequestContext ctx) {
        final ContextAwareEventLoop eventLoop = ctx.eventLoop();
        return ctx.makeContextAware(CompletableFuture.supplyAsync(() -> {
                      // false, null
                      logger.info("inEventLoop={} {}", eventLoop.inEventLoop(), RequestContext.currentOrNull());
                      return "Hello!";
                  }))
                  .thenApply(value -> {
                      // false, non-null
                      logger.info("inEventLoop={} {}", eventLoop.inEventLoop(), RequestContext.currentOrNull());
                      return value;
                  });
    }
}
