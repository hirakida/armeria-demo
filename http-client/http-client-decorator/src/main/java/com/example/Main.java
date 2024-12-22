package com.example;

import static com.example.HelloDecorator.USERNAME_ATTR;

import com.fasterxml.jackson.databind.JsonNode;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.logging.LogWriter;

public final class Main {
    public static void main(String[] args) {
        final RestClient client =
                RestClient.builder("https://api.github.com")
                          .decorator(HelloDecorator::new)
                          .decorator(LoggingClient.builder()
                                                  .logWriter(LogWriter.builder()
                                                                      .requestLogLevel(LogLevel.INFO)
                                                                      .successfulResponseLogLevel(LogLevel.INFO)
                                                                      .build())
                                                  .newDecorator())
                          .build();

        final JsonNode response = client.get("/users/hirakida")
                                        .attr(USERNAME_ATTR, "hirakida")
                                        .execute(JsonNode.class)
                                        .join()
                                        .content();
        System.out.println(response);
    }
}
