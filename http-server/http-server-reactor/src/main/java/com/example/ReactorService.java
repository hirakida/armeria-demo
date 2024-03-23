package com.example;

import java.time.Duration;
import java.time.LocalTime;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.ContextAwareEventLoop;
import com.linecorp.armeria.common.RequestContext;
import com.linecorp.armeria.common.sse.ServerSentEvent;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesEventStream;
import com.linecorp.armeria.server.annotation.ProducesJson;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ReactorService {
    private static final Logger logger = LoggerFactory.getLogger(ReactorService.class);

    @Get("/mono")
    @ProducesJson
    public Mono<LocalTime> mono() {
        return Mono.just(LocalTime.now());
    }

    @Get("/flux")
    @ProducesJson
    public Flux<LocalTime> flux() {
        return Flux.range(1, 5)
                   .delayElements(Duration.ofMillis(500))
                   .doOnNext(i -> logger.info("{} {}", i, RequestContext.currentOrNull()))
                   .map(i -> LocalTime.now());
    }

    @Get("/sse")
    @ProducesEventStream
    public Publisher<ServerSentEvent> sse(ServiceRequestContext ctx) {
        final ContextAwareEventLoop eventLoop = ctx.eventLoop();
        return Flux.interval(Duration.ofSeconds(1))
                   .take(5)
                   .publishOn(Schedulers.fromExecutor(eventLoop))
                   .doOnNext(i -> logger.info("{} {}", i, RequestContext.currentOrNull()))
                   .scan(Long::sum)
                   .map(data -> ServerSentEvent.ofData(data.toString()));
    }
}
