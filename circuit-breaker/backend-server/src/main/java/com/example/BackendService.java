package com.example;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.linecorp.armeria.common.HttpData;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Put;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BackendService {
    private final AtomicInteger statusCode = new AtomicInteger(HttpStatus.OK.code());

    @Get("/")
    public HttpResponse hello() {
        return createResponse(HttpStatus.valueOf(statusCode.get()));
    }

    @Put("/up")
    public void up() {
        statusCode.set(HttpStatus.OK.code());
    }

    @Put("/down")
    public void down() {
        statusCode.set(HttpStatus.SERVICE_UNAVAILABLE.code());
    }

    private static HttpResponse createResponse(HttpStatus httpStatus) {
        final String data = String.format("{\"code\":%d,\"reason\":\"%s\"}",
                                          httpStatus.code(),
                                          httpStatus.reasonPhrase());
        return HttpResponse.of(httpStatus, MediaType.JSON_UTF_8,
                               HttpData.ofUtf8(data));
    }
}
