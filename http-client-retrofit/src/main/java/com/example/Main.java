package com.example;

import static com.example.HelloDecorator.USERNAME_ATTR;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.client.logging.ContentPreviewingClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retrofit2.ArmeriaRetrofit;
import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.util.SafeCloseable;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        final GitHubService service = createClient();

        service.getUser("hirakida")
               .thenAccept(user -> logger.info("{}", user))
               .join();

        try (SafeCloseable ignored =
                     Clients.withContextCustomizer(ctx -> ctx.setAttr(USERNAME_ATTR, "hirakida"))) {
            service.getKeys("hirakida")
                   .doOnSuccess(response -> logger.info("{}", response))
                   .blockingSubscribe();
        }
    }

    private static GitHubService createClient() {
        final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        final Retrofit retrofit =
                ArmeriaRetrofit.builder("https://api.github.com")
                               .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                               .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                               .decorator(HelloDecorator::new)
                               .decorator(ContentPreviewingClient.newDecorator(1000))
                               .decorator(LoggingClient.builder()
                                                       .requestLogLevel(LogLevel.INFO)
                                                       .successfulResponseLogLevel(LogLevel.INFO)
                                                       .newDecorator())
                               .responseTimeout(Duration.ofSeconds(10))
                               .writeTimeout(Duration.ofSeconds(10))
                               .build();
        return retrofit.create(GitHubService.class);
    }
}
