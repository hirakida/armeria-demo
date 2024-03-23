package com.example;

import java.util.concurrent.atomic.AtomicInteger;

import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.HttpResult;
import com.linecorp.armeria.server.annotation.PathPrefix;
import com.linecorp.armeria.server.annotation.ProducesJson;
import com.linecorp.armeria.server.annotation.Put;
import com.linecorp.armeria.server.annotation.StatusCode;

@PathPrefix("/backend")
public class BackendService {
    private final AtomicInteger statusCode = new AtomicInteger(HttpStatus.OK.code());

    @Get
    @ProducesJson
    public HttpResult<Response> get() {
        final HttpStatus httpStatus = HttpStatus.valueOf(statusCode.get());
        return HttpResult.of(httpStatus, new Response(httpStatus));
    }

    @Put("/up")
    @StatusCode(204)
    public void up() {
        statusCode.set(HttpStatus.OK.code());
    }

    @Put("/down")
    @StatusCode(204)
    public void down() {
        statusCode.set(HttpStatus.SERVICE_UNAVAILABLE.code());
    }

    public record Response(int code, String reasonPhrase) {
        public Response(HttpStatus httpStatus) {
            this(httpStatus.code(), httpStatus.reasonPhrase());
        }
    }
}
