package com.example;

import java.time.Duration;
import java.util.List;

import com.example.model.Key;
import com.example.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retrofit2.ArmeriaRetrofit;

import lombok.extern.slf4j.Slf4j;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Slf4j
public class Main {

    public static void main(String[] args) {
        final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        final Retrofit retrofit =
                ArmeriaRetrofit.builder("https://api.github.com")
                               .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                               .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                               .decorator(LoggingClient.newDecorator())
                               .responseTimeout(Duration.ofSeconds(10))
                               .writeTimeout(Duration.ofSeconds(10))
                               .build();
        final GitHubService service = retrofit.create(GitHubService.class);

        final User user = service.getUser("hirakida").join();
        log.info("{}", user);

        final List<Key> keys = service.getKeys("hirakida").blockingGet();
        log.info("{}", keys);
    }
}
