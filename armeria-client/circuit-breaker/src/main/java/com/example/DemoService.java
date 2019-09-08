package com.example;

import java.util.concurrent.atomic.AtomicInteger;

import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.HttpResult;

public class DemoService {
    private static final AtomicInteger COUNTER = new AtomicInteger();

    @Get("/")
    public HttpResult<String> get() {
        final int count = COUNTER.incrementAndGet();
        if (count < 15) {
            return HttpResult.of(HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (count < 30) {
            return HttpResult.of(HttpStatus.OK);
        } else {
            COUNTER.set(0);
            return HttpResult.of(HttpStatus.OK);
        }
    }
}
