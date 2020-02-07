package com.example.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.example.ExceptionHandlerImpl;

import com.linecorp.armeria.server.annotation.ExceptionHandler;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.ProducesJson;

@ExceptionHandler(ExceptionHandlerImpl.class)
public class JsonService {

    @Get("/date")
    @ProducesJson
    public LocalDate getDate(@Param("zoneId") Optional<String> zoneId) {
        return zoneId.map(ZoneId::of)
                     .map(LocalDate::now)
                     .orElse(LocalDate.now());
    }

    @Get("/locales")
    @ProducesJson
    public List<Locale> getLocales() {
        return List.of(Locale.getAvailableLocales());
    }

    @Get("/future")
    @ProducesJson
    public CompletableFuture<Map<String, ?>> future() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return Map.of("datetime", LocalDateTime.now());
        });
    }

    private static void sleep(long timeout) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
