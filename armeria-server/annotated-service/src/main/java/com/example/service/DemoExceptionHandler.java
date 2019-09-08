package com.example.service;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.RequestContext;
import com.linecorp.armeria.server.annotation.ExceptionHandlerFunction;

public class DemoExceptionHandler implements ExceptionHandlerFunction {
    @Override
    public HttpResponse handleException(RequestContext ctx, HttpRequest req, Throwable cause) {
        if (cause instanceof IllegalArgumentException) {
            return HttpResponse.of(HttpStatus.BAD_REQUEST);
        } else {
            return HttpResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
