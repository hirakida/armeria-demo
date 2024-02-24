package com.example;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

import com.linecorp.armeria.server.annotation.Default;
import com.linecorp.armeria.server.annotation.ExceptionHandler;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.PathPrefix;
import com.linecorp.armeria.server.annotation.ProducesJson;

@PathPrefix("/json")
@ExceptionHandler(ExceptionHandlerImpl.class)
public class JsonService {
    @Get("/datetime")
    @ProducesJson
    public LocalDateTime datetime(@Param @Default("Asia/Tokyo") String zoneId) {
        return LocalDateTime.now(ZoneId.of(zoneId));
    }

    @Get("/countries")
    @ProducesJson
    public String[] countries() {
        return Locale.getISOCountries();
    }
}
