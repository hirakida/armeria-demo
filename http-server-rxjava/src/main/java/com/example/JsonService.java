package com.example;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.ContextAwareEventLoop;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesJson;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class JsonService {
    private static final Logger logger = LoggerFactory.getLogger(JsonService.class);

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
    public Flowable<LocalTime> flowable() {
        return Flowable.range(1, 5)
                       .concatMap(i -> Flowable.fromCallable(LocalTime::now)
                                               .delay(100, TimeUnit.MILLISECONDS))
                       .doOnNext(time -> logger.info("{}", time));
    }

    @Get("/delay")
    @ProducesJson
    public Flowable<Integer> delay(ServiceRequestContext ctx) {
        final ContextAwareEventLoop eventLoop = ctx.eventLoop();
        return Flowable.range(1, 3)
                       .doOnNext(i -> logger.info("inEventLoop={}", eventLoop.inEventLoop()))
                       .delay(100, TimeUnit.MILLISECONDS)
                       .doOnNext(i -> logger.info("inEventLoop={}", eventLoop.inEventLoop()))
                       .observeOn(Schedulers.from(eventLoop))
                       .doOnNext(time -> logger.info("inEventLoop={}", eventLoop.inEventLoop()));
    }
}
