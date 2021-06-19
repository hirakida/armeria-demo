package com.example;

import java.time.Duration;
import java.time.LocalTime;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.sse.ServerSentEvent;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesEventStream;
import com.linecorp.armeria.server.annotation.ProducesJson;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactorService.class);

    @Get("/mono")
    @ProducesJson
    public Mono<LocalTime> mono() {
        return Mono.just(LocalTime.now());
    }

    @Get("/flux")
    @ProducesJson
    public Flux<LocalTime> flux() {
        return Flux.just(LocalTime.now());
    }

    @Get("/sse")
    @ProducesEventStream
    public Publisher<ServerSentEvent> sse() {
        return Flux.interval(Duration.ofSeconds(1))
                   .take(5)
                   .doOnNext(i -> LOGGER.info("{}", i))
                   .scan(Long::sum)
                   .map(data -> ServerSentEvent.ofData(data.toString()));
    }
}
