package com.example.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.DecoratingHttpServiceFunction;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;

public class MethodDecorator implements DecoratingHttpServiceFunction {
    private static final Logger logger = LoggerFactory.getLogger(MethodDecorator.class);

    @Override
    public HttpResponse serve(HttpService delegate, ServiceRequestContext ctx, HttpRequest req)
            throws Exception {
        logger.info("Hello!");
        return delegate.serve(ctx, req);
    }
}
