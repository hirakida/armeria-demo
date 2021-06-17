package com.example;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retrofit2.ArmeriaRetrofit;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Retrofit retrofit =
                ArmeriaRetrofit.builder("https://api.github.com")
                               .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                               .decorator(LoggingClient.newDecorator())
                               .responseTimeout(Duration.ofSeconds(10))
                               .writeTimeout(Duration.ofSeconds(10))
                               .build();
        GitHubService service = retrofit.create(GitHubService.class);

        User user = service.getUser("hirakida").join();
        LOGGER.info("{}", user);
    }
}
