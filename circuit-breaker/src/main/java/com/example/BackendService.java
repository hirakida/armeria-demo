package com.example;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.linecorp.armeria.common.HttpData;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.PathPrefix;
import com.linecorp.armeria.server.annotation.Put;

import lombok.extern.slf4j.Slf4j;

@Component
@PathPrefix("/backend")
@Slf4j
public class BackendService {
    private final AtomicInteger statusCode = new AtomicInteger(HttpStatus.OK.code());

    @Get
    public HttpResponse get() {
        final HttpStatus httpStatus = HttpStatus.valueOf(statusCode.get());
        final String data = "{\"code\":%d,\"reason\":\"%s\"}"
                .formatted(httpStatus.code(), httpStatus.reasonPhrase());
        return HttpResponse.of(httpStatus, MediaType.JSON_UTF_8, HttpData.ofUtf8(data));
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
