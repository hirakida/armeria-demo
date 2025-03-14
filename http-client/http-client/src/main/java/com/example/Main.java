package com.example;

import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.Repo;
import com.example.model.User;
import com.fasterxml.jackson.core.type.TypeReference;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.ConnectionPoolListener;
import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.logging.ContentPreviewingClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.logging.LogWriter;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final TypeReference<List<Repo>> TYPE_REFERENCE = new TypeReference<>() {};

    public static void main(String[] args) {
        final RestClient client = createClient();

        final User user = client.get("/users/{username}")
                                .pathParam("username", "hirakida")
                                .execute(User.class)
                                .join()
                                .content();
        logger.info("{}", user);

        final List<Repo> repos = client.get("/users/{username}/repos")
                                       .pathParam("username", "hirakida")
                                       .execute(TYPE_REFERENCE)
                                       .join()
                                       .content();
        repos.forEach(repo -> logger.info("{}", repo));
    }

    private static RestClient createClient() {
        final ClientFactory factory = ClientFactory.builder()
                                                   .connectTimeout(Duration.ofSeconds(10))
                                                   .idleTimeout(Duration.ofSeconds(10))
                                                   .connectionPoolListener(ConnectionPoolListener.logging())
                                                   .build();
        return RestClient.builder("https://api.github.com")
                         .decorator(ContentPreviewingClient.newDecorator(1000))
                         .decorator(LoggingClient.builder()
                                                 .logWriter(LogWriter.builder()
                                                                     .requestLogLevel(LogLevel.INFO)
                                                                     .successfulResponseLogLevel(LogLevel.INFO)
                                                                     .build())
                                                 .newDecorator())
                         .responseTimeout(Duration.ofSeconds(10))
                         .writeTimeout(Duration.ofSeconds(10))
                         .factory(factory)
                         .build();
    }
}
