package com.example.decorator;

import com.linecorp.armeria.client.ClientRequestContext;
import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.client.SimpleDecoratingHttpClient;
import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;

import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingDecorator extends SimpleDecoratingHttpClient {
    public static final AttributeKey<String> USERNAME_ATTR =
            AttributeKey.valueOf(String.class, "USERNAME_ATTR");

    public LoggingDecorator(HttpClient delegate) {
        super(delegate);
    }

    @Override
    public HttpResponse execute(ClientRequestContext ctx, HttpRequest req) throws Exception {
        if (ctx.hasAttr(USERNAME_ATTR)) {
            log.info("attr={}", ctx.attr(USERNAME_ATTR));
        }
        return unwrap().execute(ctx, req);
    }
}