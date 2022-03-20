package com.example.decorator;

import java.time.LocalDateTime;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.SimpleDecoratingHttpService;

import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeDecorator extends SimpleDecoratingHttpService {
    public static final AttributeKey<LocalDateTime> DATETIME_ATTR =
            AttributeKey.valueOf(LocalDateTime.class, "DATETIME_ATTR");

    public DateTimeDecorator(HttpService delegate) {
        super(delegate);
    }

    @Override
    public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        final LocalDateTime now = LocalDateTime.now();
        log.info("{}", now);
        ctx.setAttr(DATETIME_ATTR, now);
        return unwrap().serve(ctx, req);
    }
}
