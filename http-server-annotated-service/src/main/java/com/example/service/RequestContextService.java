package com.example.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.RequestContext;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Get;

public class RequestContextService {
    private static final Logger logger = LoggerFactory.getLogger(RequestContextService.class);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Get("/ctx")
    public void context(ServiceRequestContext ctx) {
        logger.info("{}", ctx);

        ctx.makeContextAware(() -> logger.info("{}", getRequestContext()))
           .run();
        ctx.makeContextAware(executorService)
           .execute(() -> logger.info("{}", getRequestContext()));
        executorService.execute(() -> logger.info("{}", getRequestContext()));  // null
    }

    @Nullable
    private static ServiceRequestContext getRequestContext() {
        return RequestContext.currentOrNull();
    }
}
