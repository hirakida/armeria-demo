package com.example;

import java.time.LocalDateTime;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesJson;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorService {

    @Get("/mono")
    @ProducesJson
    public Mono<LocalDateTime> mono() {
        return Mono.just(LocalDateTime.now());
    }

    @Get("/flux")
    @ProducesJson
    public Flux<LocalDateTime> flux() {
        return Flux.just(LocalDateTime.now());
    }
}
