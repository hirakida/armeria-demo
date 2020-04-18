package com.example;

import java.util.concurrent.TimeUnit;

import org.reactivestreams.Publisher;

import com.linecorp.armeria.common.sse.ServerSentEvent;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesEventStream;

import io.reactivex.Flowable;

public class SseService {

    @Get("/sse")
    @ProducesEventStream
    public Publisher<ServerSentEvent> sse() {
        return Flowable.interval(1, TimeUnit.SECONDS)
                       .take(5)
                       .scan(Long::sum)
                       .map(data -> ServerSentEvent.ofData(data.toString()));
    }
}
