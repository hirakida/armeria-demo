package com.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.ContextAwareEventLoop;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.RequestContext;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Blocking;
import com.linecorp.armeria.server.annotation.Get;

public class BlockingService {
    private static final Logger logger = LoggerFactory.getLogger(BlockingService.class);

    @Blocking
    @Get("/blocking1")
    public String blocking1(ServiceRequestContext ctx) {
        final ContextAwareEventLoop eventLoop = ctx.eventLoop();
        // false, non-null
        logger.info("inEventLoop={} {}", eventLoop.inEventLoop(), RequestContext.currentOrNull());
        sleep(1);
        logger.info("inEventLoop={} {}", eventLoop.inEventLoop(), RequestContext.currentOrNull());
        return "Hello!";
    }

    @Get("/blocking2")
    public HttpResponse blocking2(ServiceRequestContext ctx) {
        final ContextAwareEventLoop eventLoop = ctx.eventLoop();
        // true, non-null
        logger.info("inEventLoop={} {}", eventLoop.inEventLoop(), RequestContext.currentOrNull());

        final CompletableFuture<HttpResponse> future = new CompletableFuture<>();
        ctx.blockingTaskExecutor()
           .execute(() -> {
               sleep(1);
               // false, non-null
               logger.info("inEventLoop={} {}", eventLoop.inEventLoop(), RequestContext.currentOrNull());
               future.complete(HttpResponse.of("Hello!"));
           });
        return HttpResponse.of(future);
    }

    @Get("/blocking3")
    public HttpResponse blocking3(ServiceRequestContext ctx) {
        ctx.makeContextAware(ctx.blockingTaskExecutor())
           .execute(() -> {
               sleep(1);
               // false, non-null
               logger.info("inEventLoop={} {}", ctx.eventLoop().inEventLoop(), RequestContext.currentOrNull());
           });
        return HttpResponse.of(HttpStatus.OK);
    }

    private static void sleep(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException ignored) {}
    }
}
