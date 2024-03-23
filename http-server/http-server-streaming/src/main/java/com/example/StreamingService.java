package com.example;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.sse.ServerSentEvent;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesEventStream;
import com.linecorp.armeria.server.annotation.ProducesJsonSequences;
import com.linecorp.armeria.server.streaming.JsonLines;
import com.linecorp.armeria.server.streaming.JsonTextSequences;
import com.linecorp.armeria.server.streaming.ServerSentEvents;

public class StreamingService {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Get("/json-seq1")
    @ProducesJsonSequences
    public Stream<Locale> jsonSeq1() {
        return Stream.of(Locale.getAvailableLocales());
    }

    @Get("/json-seq2")
    public HttpResponse jsonSeq2() {
        return JsonTextSequences.fromObject(Locale.getISOCountries());
    }

    @Get("/ndjson")
    public HttpResponse ndjson() {
        return JsonLines.fromObject(Locale.getISOLanguages());
    }

    @Get("/sse1")
    @ProducesEventStream
    public Stream<ServerSentEvent> sse1() {
        return Stream.of(Locale.getISOCountries())
                     .map(ServerSentEvent::ofData);
    }

    @Get("/sse2")
    public HttpResponse sse2() {
        final Stream<ServerSentEvent> locales = Stream.of(Locale.getISOCountries())
                                                      .map(ServerSentEvent::ofData);
        return ServerSentEvents.fromStream(locales, executorService);
    }
}
