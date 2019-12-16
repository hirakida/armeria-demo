package com.example;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Head;
import com.linecorp.armeria.server.annotation.Put;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BackendService {
    public static final String HEALTH_CHECK_PATH = "/l7check";
    private final int port;
    private HttpStatus status = HttpStatus.OK;

    @Get("/")
    public String get() {
        return "localhost:" + port;
    }

    @Head(HEALTH_CHECK_PATH)
    public HttpResponse healthCheck() {
        return HttpResponse.of(status);
    }

    @Put(HEALTH_CHECK_PATH)
    public void changeStatus() {
        status = status == HttpStatus.OK ? HttpStatus.SERVICE_UNAVAILABLE : HttpStatus.OK;
    }
}
