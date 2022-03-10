package com.example;

import java.util.concurrent.TimeUnit;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.ContextAwareEventLoop;
import com.linecorp.armeria.common.sse.ServerSentEvent;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesEventStream;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EventStreamService {
    private static final Logger logger = LoggerFactory.getLogger(EventStreamService.class);

    @Get("/sse")
    @ProducesEventStream
    public Publisher<ServerSentEvent> sse(ServiceRequestContext ctx) {
        final ContextAwareEventLoop eventLoop = ctx.eventLoop();
        return Flowable.interval(100, TimeUnit.MILLISECONDS)
                       .take(5)
                       .observeOn(Schedulers.from(eventLoop))
                       .doOnNext(i -> logger.info("{}", i))
                       .scan(Long::sum)
                       .map(data -> ServerSentEvent.ofData(data.toString()));
    }
}
