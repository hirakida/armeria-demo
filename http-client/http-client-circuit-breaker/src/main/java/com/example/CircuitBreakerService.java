package com.example;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.BackendService.Response;

import com.linecorp.armeria.client.InvalidHttpResponseException;
import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.PathPrefix;
import com.linecorp.armeria.server.annotation.ProducesJson;

@PathPrefix("/frontend")
public class CircuitBreakerService {
    private static final Logger logger = LoggerFactory.getLogger(CircuitBreakerService.class);
    private final RestClient restClient;

    public CircuitBreakerService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Get
    @ProducesJson
    public CompletableFuture<Response> get() {
        return restClient.get("/backend")
                         .execute(Response.class)
                         .handle((entity, e) -> {
                             if (e != null) {
                                 logger.warn("{}", e.getMessage());
                                 if (e.getCause() instanceof InvalidHttpResponseException httpResponseException) {
                                     return new Response(httpResponseException.response().status());
                                 }
                                 return new Response(0, "");
                             }
                             return entity.content();
                         });
    }
}
