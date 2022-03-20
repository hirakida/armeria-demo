package com.example.decorator;

import static com.example.decorator.AttributeKeys.DATETIME_ATTR;
import static com.example.decorator.AttributeKeys.USERNAME_ATTR;

import com.linecorp.armeria.client.ClientRequestContext;
import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.client.SimpleDecoratingHttpClient;
import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingDecorator extends SimpleDecoratingHttpClient {
    public LoggingDecorator(HttpClient delegate) {
        super(delegate);
    }

    @Override
    public HttpResponse execute(ClientRequestContext ctx, HttpRequest req) throws Exception {
        log.info("Hello!");
        if (ctx.hasAttr(DATETIME_ATTR)) {
            log.info("datetime={}", ctx.attr(DATETIME_ATTR));
        }
        if (ctx.hasAttr(USERNAME_ATTR)) {
            log.info("username={}", ctx.attr(USERNAME_ATTR));
        }
        return unwrap().execute(ctx, req);
    }
}
