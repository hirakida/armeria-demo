package com.example.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.linecorp.armeria.common.ContextAwareScheduledExecutorService;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Blocking;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.PathPrefix;

import lombok.extern.slf4j.Slf4j;

@PathPrefix("/blocking")
@Slf4j
public class BlockingService {

    @Blocking
    @Get("/1")
    public String blocking1(ServiceRequestContext ctx) {
        log.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
        sleep(1);
        log.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
        return "Hello!";
    }

    @Get("/2")
    public HttpResponse blocking2(ServiceRequestContext ctx) {
        log.info("inEventLoop={}", ctx.eventLoop().inEventLoop());

        final CompletableFuture<HttpResponse> future = new CompletableFuture<>();
        ctx.blockingTaskExecutor().execute(() -> {
            sleep(1);
            log.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
            future.complete(HttpResponse.of("Hello!"));
        });
        return HttpResponse.from(future);
    }

    @Get("/3")
    public HttpResponse blocking3(ServiceRequestContext ctx) {
        log.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
        ctx.makeContextAware(() -> log.info("inEventLoop={}", ctx.eventLoop().inEventLoop()))
           .run();

        final ContextAwareScheduledExecutorService executorService = ctx.blockingTaskExecutor();
        ctx.makeContextAware(executorService)
           .execute(() -> {
               sleep(1);
               log.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
           });

        log.info("inEventLoop={}", ctx.eventLoop().inEventLoop());
        return HttpResponse.of(HttpStatus.OK);
    }

    private static void sleep(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException ignored) {}
    }
}
