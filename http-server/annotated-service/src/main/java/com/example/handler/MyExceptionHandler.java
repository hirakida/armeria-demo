package com.example.handler;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.ExceptionHandlerFunction;

public class MyExceptionHandler implements ExceptionHandlerFunction {
    @Override
    public HttpResponse handleException(ServiceRequestContext ctx, HttpRequest req, Throwable cause) {
        if (cause instanceof IllegalArgumentException) {
            return HttpResponse.of(HttpStatus.BAD_REQUEST);
        }
        return ExceptionHandlerFunction.fallthrough();
    }
}
