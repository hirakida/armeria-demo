package com.example.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Blocking;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.PathPrefix;

@PathPrefix("/blocking")
public class BlockingService {
    private static final Logger logger = LoggerFactory.getLogger(BlockingService.class);

    @Blocking
    @Get("/1")
    public String blocking1(ServiceRequestContext ctx) {
        logger.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
        sleep(1);
        logger.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
        return "Hello!";
    }

    @Get("/2")
    public HttpResponse blocking2(ServiceRequestContext ctx) {
        logger.info("inEventLoop={}", ctx.eventLoop().inEventLoop());

        final CompletableFuture<HttpResponse> future = new CompletableFuture<>();
        ctx.blockingTaskExecutor()
           .execute(() -> {
               sleep(1);
               logger.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
               future.complete(HttpResponse.of("Hello!"));
           });
        return HttpResponse.of(future);
    }

    @Get("/3")
    public HttpResponse blocking3(ServiceRequestContext ctx) {
        logger.info("inEventLoop={}", ctx.eventLoop().inEventLoop());

        ctx.makeContextAware(ctx.blockingTaskExecutor())
           .execute(() -> {
               sleep(1);
               logger.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
           });
        return HttpResponse.of(HttpStatus.OK);
    }

    private static void sleep(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException ignored) {}
    }
}
