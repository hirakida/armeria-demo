package com.example.service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Publisher;

import com.linecorp.armeria.common.sse.ServerSentEvent;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesEventStream;

import io.reactivex.Flowable;
import reactor.core.publisher.Flux;

public class SseService {

    @Get("/sse1")
    @ProducesEventStream
    public Publisher<ServerSentEvent> sse1() {
        return Flux.interval(Duration.ofSeconds(1))
                   .take(5)
                   .scan(Long::sum)
                   .map(data -> ServerSentEvent.ofData(data.toString()));
    }

    @Get("/sse2")
    @ProducesEventStream
    public Publisher<ServerSentEvent> sse2() {
        return Flowable.interval(1, TimeUnit.SECONDS)
                       .take(5)
                       .scan(Long::sum)
                       .map(data -> ServerSentEvent.ofData(data.toString()));
    }
}
