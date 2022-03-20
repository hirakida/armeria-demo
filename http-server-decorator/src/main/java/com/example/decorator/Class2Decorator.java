package com.example.decorator;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.DecoratingHttpServiceFunction;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Class2Decorator implements DecoratingHttpServiceFunction {
    @Override
    public HttpResponse serve(HttpService delegate, ServiceRequestContext ctx, HttpRequest req)
            throws Exception {
        log.info("Hello!");
        return delegate.serve(ctx, req);
    }
}
