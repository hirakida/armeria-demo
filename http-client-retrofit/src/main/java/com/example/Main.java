package com.example;

import static com.example.decorator.LoggingDecorator.USERNAME_ATTR;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import com.example.decorator.LoggingDecorator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retrofit2.ArmeriaRetrofit;
import com.linecorp.armeria.common.util.SafeCloseable;

import lombok.extern.slf4j.Slf4j;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Slf4j
public final class Main {

    public static void main(String[] args) throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        final Retrofit retrofit =
                ArmeriaRetrofit.builder("https://api.github.com")
                               .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                               .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                               .decorator(LoggingDecorator::new)
                               .decorator(LoggingClient.newDecorator())
                               .responseTimeout(Duration.ofSeconds(10))
                               .writeTimeout(Duration.ofSeconds(10))
                               .build();
        final GitHubService service = retrofit.create(GitHubService.class);

        try (SafeCloseable ignored =
                     Clients.withContextCustomizer(ctx -> ctx.setAttr(USERNAME_ATTR, "hirakida"))) {
            service.getUser("hirakida")
                   .thenAccept(user -> log.info("{}", user));
            service.getKeys("hirakida")
                   .subscribe(res -> log.info("{}", res));
        }

        TimeUnit.SECONDS.sleep(2);
    }
}
