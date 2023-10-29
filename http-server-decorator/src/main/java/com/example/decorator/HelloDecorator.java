package com.example.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.SimpleDecoratingHttpService;

public class HelloDecorator extends SimpleDecoratingHttpService {
    private static final Logger logger = LoggerFactory.getLogger(HelloDecorator.class);
    private final String message;

    public HelloDecorator(HttpService delegate, String message) {
        super(delegate);
        this.message = message;
    }

    @Override
    public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        logger.info(message);
        return unwrap().serve(ctx, req);
    }
}
