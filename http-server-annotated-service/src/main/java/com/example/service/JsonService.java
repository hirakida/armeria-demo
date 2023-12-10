package com.example.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.ExceptionHandlerImpl;

import com.linecorp.armeria.server.annotation.Default;
import com.linecorp.armeria.server.annotation.ExceptionHandler;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.Post;
import com.linecorp.armeria.server.annotation.ProducesJson;
import com.linecorp.armeria.server.annotation.ProducesJsonSequences;
import com.linecorp.armeria.server.annotation.RequestObject;

@ExceptionHandler(ExceptionHandlerImpl.class)
public class JsonService {
    @Get("/date")
    @ProducesJson
    public LocalDate getDate(@Param Optional<String> zoneId) {
        return zoneId.map(ZoneId::of)
                     .map(LocalDate::now)
                     .orElse(LocalDate.now());
    }

    @Get("/datetime")
    @ProducesJson
    public LocalDateTime getDateTime(@Param @Default("Asia/Tokyo") String zoneId) {
        return LocalDateTime.now(ZoneId.of(zoneId));
    }

    @Get("/datetimes")
    @ProducesJson
    public Map<ZoneId, LocalDateTime> getDateTimes(@Param Set<String> zoneIds) {
        return zoneIds.stream()
                      .map(ZoneId::of)
                      .map(zone -> Map.entry(zone, LocalDateTime.now(zone)))
                      .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
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

    @Get("/json_data")
    @ProducesJson
    public JsonResponse getJson() {
        return new JsonResponse("Hello!", LocalDateTime.now());
    }

    @Post("/json_data")
    @ProducesJson
    public JsonResponse postJson(@RequestObject JsonRequest request) {
        return new JsonResponse(request.message(), LocalDateTime.now());
    }

    public record JsonRequest(String message) {}

    public record JsonResponse(String message, LocalDateTime dateTime) {}
}
