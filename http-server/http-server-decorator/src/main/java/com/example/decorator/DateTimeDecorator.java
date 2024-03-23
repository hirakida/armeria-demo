package com.example.decorator;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.SimpleDecoratingHttpService;

import io.netty.util.AttributeKey;

public class DateTimeDecorator extends SimpleDecoratingHttpService {
    public static final AttributeKey<LocalDateTime> DATETIME_ATTR =
            AttributeKey.valueOf(LocalDateTime.class, "DATETIME_ATTR");
    private static final Logger logger = LoggerFactory.getLogger(DateTimeDecorator.class);

    public DateTimeDecorator(HttpService delegate) {
        super(delegate);
    }

    @Override
    public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        final LocalDateTime now = LocalDateTime.now();
        logger.info("{}", now);
        try {
            ctx.setAttr(DATETIME_ATTR, now);
            return unwrap().serve(ctx, req);
        } catch (Exception e) {
            return HttpResponse.ofFailure(e);
        }
    }
}
