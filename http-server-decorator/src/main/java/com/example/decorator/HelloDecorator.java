package com.example.decorator;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.SimpleDecoratingHttpService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloDecorator extends SimpleDecoratingHttpService {
    private final String message;

    public HelloDecorator(HttpService delegate, String message) {
        super(delegate);
        this.message = message;
    }

    @Override
    public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req)
            throws Exception {
        log.info(message);
        return unwrap().serve(ctx, req);
    }
}
