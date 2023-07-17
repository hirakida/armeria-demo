package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.client.ClientRequestContext;
import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.client.SimpleDecoratingHttpClient;
import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;

import io.netty.util.AttributeKey;

public class LoggingDecorator extends SimpleDecoratingHttpClient {
    public static final AttributeKey<String> USERNAME_ATTR =
            AttributeKey.valueOf(String.class, "USERNAME_ATTR");
    private static final Logger logger = LoggerFactory.getLogger(LoggingDecorator.class);

    public LoggingDecorator(HttpClient delegate) {
        super(delegate);
    }

    @Override
    public HttpResponse execute(ClientRequestContext ctx, HttpRequest req) throws Exception {
        if (ctx.hasAttr(USERNAME_ATTR)) {
            logger.info("username={}", ctx.attr(USERNAME_ATTR));
        }
        return unwrap().execute(ctx, req);
    }
}
