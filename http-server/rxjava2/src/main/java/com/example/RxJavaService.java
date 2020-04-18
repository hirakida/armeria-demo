package com.example;

import java.time.LocalDateTime;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesJson;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class RxJavaService {

    @Get("/single")
    @ProducesJson
    public Single<LocalDateTime> single() {
        return Single.just(LocalDateTime.now());
    }

    @Get("/maybe")
    @ProducesJson
    public Maybe<LocalDateTime> maybe() {
        return Maybe.just(LocalDateTime.now());
    }

    @Get("/flowable")
    @ProducesJson
    public Flowable<LocalDateTime> flowable() {
        return Flowable.just(LocalDateTime.now());
    }
}
