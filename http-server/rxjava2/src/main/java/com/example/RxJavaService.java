package com.example;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesJson;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class RxJavaService {
    private static final Logger log = LoggerFactory.getLogger(RxJavaService.class);

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
    public Flowable<LocalTime> flowable(ServiceRequestContext ctx) {
        ExecutorService executor = ctx.eventLoop();
        return Flowable.just(LocalTime.now())
                       .observeOn(Schedulers.newThread())
                       .doOnNext(time -> log.info("{}", time))
                       .observeOn(Schedulers.from(executor))
                       .doOnNext(time -> log.info("{}", time));
    }
}
