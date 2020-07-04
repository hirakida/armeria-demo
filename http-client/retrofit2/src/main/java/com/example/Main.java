package com.example;

import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retrofit2.ArmeriaRetrofit;

import lombok.extern.slf4j.Slf4j;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Slf4j
public class Main {

    public static void main(String[] args) {
        GitHubService service = createService();
        service.getUser("hirakida")
               .whenComplete((user, e) -> {
                   if (e == null) {
                       log.info("{}", user);
                   }
               }).join();
    }

    private static GitHubService createService() {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Retrofit retrofit =
                ArmeriaRetrofit.builder("https://api.github.com")
                               .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                               .decorator(LoggingClient.newDecorator())
                               .responseTimeout(Duration.ofSeconds(10))
                               .writeTimeout(Duration.ofSeconds(10))
                               .build();
        return retrofit.create(GitHubService.class);
    }
}
