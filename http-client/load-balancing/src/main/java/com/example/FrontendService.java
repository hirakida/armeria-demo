package com.example;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Get;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FrontendService {
    private final WebClient webClient;

    @Get("/")
    public HttpResponse get() {
        return webClient.get("/");
    }
}
