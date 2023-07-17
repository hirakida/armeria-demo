package com.example;

import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.Repo;
import com.example.model.User;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.ConnectionPoolListener;
import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.logging.ContentPreviewingClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.logging.LogLevel;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        final ClientFactory factory = ClientFactory.builder()
                                                   .connectTimeout(Duration.ofSeconds(10))
                                                   .idleTimeout(Duration.ofSeconds(10))
                                                   .connectionPoolListener(ConnectionPoolListener.logging())
                                                   .build();
        final RestClient restClient =
                RestClient.builder("https://api.github.com")
                          .decorator(LoggingDecorator::new)
                          .decorator(ContentPreviewingClient.newDecorator(1000))
                          .decorator(LoggingClient.builder()
                                                  .requestLogLevel(LogLevel.INFO)
                                                  .successfulResponseLogLevel(LogLevel.INFO)
                                                  .newDecorator())
                          .responseTimeout(Duration.ofSeconds(10))
                          .writeTimeout(Duration.ofSeconds(10))
                          .factory(factory)
                          .build();
        final GitHubClient client = new GitHubClient(restClient);

        User user = client.getUser("hirakida")
                          .join()
                          .content();
        logger.info("{}", user);

        user = client.getUserWithAttr("hirakida")
                     .join()
                     .content();
        logger.info("{}", user);

        final List<Repo> repos = client.getRepos("hirakida")
                                       .join()
                                       .content();
        repos.forEach(repo -> logger.info("{}", repo));
    }
}
