package com.example.frontend;

import org.springframework.stereotype.Component;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Get;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FrontendService {
    private final WebClient webClient;

    @Get("/")
    public HttpResponse hello() {
        return webClient.get("/");
    }
}
