package com.example;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.HttpEntity;
import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.logging.LogWriter;
import com.linecorp.armeria.common.util.AsyncLoader;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        final RestClient client =
                RestClient.builder("https://api.github.com")
                          .decorator(LoggingClient.builder()
                                                  .logWriter(LogWriter.builder()
                                                                      .requestLogLevel(LogLevel.INFO)
                                                                      .successfulResponseLogLevel(LogLevel.INFO)
                                                                      .build())
                                                  .newDecorator())
                          .build();

        final Function<JsonNode, CompletableFuture<JsonNode>> loader =
                cache -> client.get("/users/{username}")
                               .pathParam("username", "hirakida")
                               .execute(JsonNode.class)
                               .thenApply(HttpEntity::content);
        final AsyncLoader<JsonNode> asyncLoader =
                AsyncLoader.builder(loader)
                           .expireAfterLoad(Duration.ofSeconds(10))
                           .build();

        logger.info("{}", asyncLoader.load().join());
        logger.info("{}", asyncLoader.load().join());
        logger.info("{}", asyncLoader.load().join());
        TimeUnit.SECONDS.sleep(11);
        logger.info("{}", asyncLoader.load().join());
    }
}
