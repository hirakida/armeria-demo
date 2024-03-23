package com.example.backend;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.PathPrefix;
import com.linecorp.armeria.server.annotation.ProducesJson;

@PathPrefix("/backend")
public class BackendService {
    private final int port;

    public BackendService(int port) {
        this.port = port;
    }

    @Get
    @ProducesJson
    public Response get() {
        return new Response("localhost:" + port);
    }

    public record Response(String message) {}
}
