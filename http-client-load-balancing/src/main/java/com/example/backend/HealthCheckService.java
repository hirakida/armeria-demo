package com.example.backend;

import java.util.concurrent.atomic.AtomicInteger;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Head;
import com.linecorp.armeria.server.annotation.PathPrefix;
import com.linecorp.armeria.server.annotation.Put;

@PathPrefix("/internal/l7check")
public class HealthCheckService {
    private final AtomicInteger statusCode = new AtomicInteger(HttpStatus.OK.code());

    @Head("/")
    public HttpResponse healthCheck() {
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
