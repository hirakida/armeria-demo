package com.example;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

import com.linecorp.armeria.common.HttpData;
import com.linecorp.armeria.common.HttpHeaderNames;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.ResponseEntity;
import com.linecorp.armeria.common.ResponseHeaders;
import com.linecorp.armeria.server.annotation.Description;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.ProducesBinary;

@Description("Hello class")
public class HelloService {
    @Get("/hello1")
    @Description("Hello1 method")
    public String hello1() {
        return "Hello!";
    }

    @Get("/hello2/{name}")
    public ResponseEntity<String> hello2(@Param String name) {
        final ResponseHeaders headers = ResponseHeaders.builder()
                                                       .status(HttpStatus.OK)
                                                       .add(HttpHeaderNames.CACHE_CONTROL, "no-cache")
                                                       .build();
        return ResponseEntity.of(headers, "Hello, %s!".formatted(name));
    }

    @Get("/delayed")
    public HttpResponse delayed() {
        return HttpResponse.delayed(HttpResponse.of("Hello!"),
                                    Duration.ofSeconds(3));
    }

    @Get("/binary")
    @ProducesBinary
    public HttpData binary() {
        return HttpData.of(StandardCharsets.UTF_8, "Hello!");
    }
}
