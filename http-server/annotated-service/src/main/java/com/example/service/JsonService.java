package com.example.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import com.example.ExceptionHandlerImpl;

import com.linecorp.armeria.server.annotation.Default;
import com.linecorp.armeria.server.annotation.ExceptionHandler;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.ProducesJson;
import com.linecorp.armeria.server.annotation.ProducesJsonSequences;

@ExceptionHandler(ExceptionHandlerImpl.class)
public class JsonService {

    @Get("/date")
    @ProducesJson
    public LocalDate getDate(@Param("zoneId") Optional<String> zoneId) {
        return zoneId.map(ZoneId::of)
                     .map(LocalDate::now)
                     .orElse(LocalDate.now());
    }

    @Get("/datetime")
    @ProducesJson
    public LocalDateTime getDateTime(@Param("zoneId") @Default("Japan") String zoneId) {
        return LocalDateTime.now(ZoneId.of(zoneId));
    }

    @Get("/locales")
    @ProducesJson
    public List<Locale> getLocales() {
        return List.of(Locale.getAvailableLocales());
    }

    @Get("/json_sequences")
    @ProducesJsonSequences
    public Stream<Locale> jsonSequences() {
        return Stream.of(Locale.getAvailableLocales());
    }
}
