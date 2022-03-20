package com.example.decorator;

import static com.example.decorator.AttributeKeys.DATETIME_ATTR;

import java.time.LocalDateTime;

import com.linecorp.armeria.client.ClientRequestContext;
import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.client.SimpleDecoratingHttpClient;
import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;

public class DateTimeDecorator extends SimpleDecoratingHttpClient {
    public DateTimeDecorator(HttpClient delegate) {
        super(delegate);
    }

    @Override
    public HttpResponse execute(ClientRequestContext ctx, HttpRequest req) throws Exception {
        ctx.setAttr(DATETIME_ATTR, LocalDateTime.now());
        return unwrap().execute(ctx, req);
    }
}
