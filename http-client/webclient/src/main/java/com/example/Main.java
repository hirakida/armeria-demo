package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.ClientOptions;
import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void main(String[] args) {
        WebClient webClient = createWebClient();
        AggregatedHttpResponse response = webClient.get("/users/hirakida").aggregate().join();

        try {
            User user = OBJECT_MAPPER.readValue(response.content().toReaderUtf8(), User.class);
            log.info("{}", user);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static WebClient createWebClient() {
        ClientFactory factory = ClientFactory.builder()
                                             .connectTimeout(Duration.ofSeconds(10))
                                             .idleTimeout(Duration.ofSeconds(10))
                                             .build();
        ClientOptions options = ClientOptions.builder()
                                             .decorator(LoggingClient.newDecorator())
                                             .responseTimeout(Duration.ofSeconds(10))
                                             .writeTimeout(Duration.ofSeconds(10))
                                             .build();
        WebClient webClient = WebClient.builder("https://api.github.com")
                                       .factory(factory)
                                       .options(options)
                                       .build();
        return webClient;
    }
}
