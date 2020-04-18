package com.example;

import java.time.Duration;

import org.reactivestreams.Publisher;

import com.linecorp.armeria.common.sse.ServerSentEvent;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesEventStream;

import reactor.core.publisher.Flux;

public class SseService {

    @Get("/sse")
    @ProducesEventStream
    public Publisher<ServerSentEvent> sse() {
        return Flux.interval(Duration.ofSeconds(1))
                   .take(5)
                   .scan(Long::sum)
                   .map(data -> ServerSentEvent.ofData(data.toString()));
    }
}
