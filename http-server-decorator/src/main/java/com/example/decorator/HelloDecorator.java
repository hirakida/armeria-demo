package com.example.decorator;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.DecoratingHttpServiceFunction;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class HelloDecorator implements DecoratingHttpServiceFunction {
    private final String message;

    @Override
    public HttpResponse serve(HttpService delegate, ServiceRequestContext ctx, HttpRequest req)
            throws Exception {
        log.info(message);
        return delegate.serve(ctx, req);
    }
}
