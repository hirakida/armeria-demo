package com.example;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.PathPrefix;
import com.linecorp.armeria.server.annotation.Put;

@Component
@PathPrefix("/backend")
public class BackendService {
    private final AtomicInteger statusCode = new AtomicInteger(HttpStatus.OK.code());

    @Get
    public HttpResponse get() {
        return HttpResponse.of(statusCode.get());
    }

    @Put("/up")
    public void up() {
        statusCode.set(HttpStatus.OK.code());
    }

    @Put("/down")
    public void down() {
        statusCode.set(HttpStatus.SERVICE_UNAVAILABLE.code());
    }
}
