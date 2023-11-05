package com.example;

import org.springframework.stereotype.Component;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Get;

@Component
public class FrontendService {
    private final WebClient webClient;

    public FrontendService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Get("/")
    public HttpResponse hello() {
        return webClient.get("/");
    }
}
