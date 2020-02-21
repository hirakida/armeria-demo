package com.example.backend;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Head;
import com.linecorp.armeria.server.annotation.Put;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class BackendService {
    public static final String HEALTH_CHECK_PATH = "/l7check";
    private final int port;
    private HttpStatus status = HttpStatus.OK;

    @Get("/")
    public String hello() {
        return "localhost:" + port;
    }

    @Head(HEALTH_CHECK_PATH)
    public HttpResponse healthCheck() {
        return HttpResponse.of(status);
    }

    @Put(HEALTH_CHECK_PATH)
    public void changeStatus() {
        if (status == HttpStatus.OK) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
        } else {
            status = HttpStatus.OK;
        }
        log.info("status={}", status);
    }
}
