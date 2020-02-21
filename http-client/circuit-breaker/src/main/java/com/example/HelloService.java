package com.example;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.HttpResult;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HelloService {
    private static final AtomicInteger COUNTER = new AtomicInteger();

    @Get("/")
    public HttpResult<String> hello() {
        final int count = COUNTER.incrementAndGet();
        log.info("{}", count);

        if (count < 15) {
            return HttpResult.of(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return HttpResult.of(HttpStatus.OK);
    }
}
