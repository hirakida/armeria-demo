package com.example.decorator;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.SimpleDecoratingHttpService;

import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeDecoratingService extends SimpleDecoratingHttpService {
    public static final AttributeKey<LocalDateTime> ATTRIBUTE_KEY =
            AttributeKey.valueOf(LocalDateTime.class, "DATETIME_ATTR");

    public DateTimeDecoratingService(HttpService delegate) {
        super(delegate);
    }

    public static Function<? super HttpService, DateTimeDecoratingService> newDecorator() {
        return service -> service.decorate(DateTimeDecoratingService::new);
    }

    public static Optional<LocalDateTime> getDateTime(ServiceRequestContext ctx) {
        return Optional.ofNullable(ctx.attr(ATTRIBUTE_KEY));
    }

    @Override
    public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        log.info("{}", now);
        ctx.setAttr(ATTRIBUTE_KEY, now);
        return unwrap().serve(ctx, req);
    }
}
