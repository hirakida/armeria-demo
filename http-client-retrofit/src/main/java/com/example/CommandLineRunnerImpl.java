package com.example;

import java.time.Duration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.retrofit2.ArmeriaRetrofit;

import lombok.extern.slf4j.Slf4j;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {
    private static final String BASE_URL = "https://api.github.com";

    @Override
    public void run(String... args) throws Exception {
        GitHubService service = createGitHubService();
        User user = service.getUser("hirakida").join();
        log.info("{}", user);
    }

    private static GitHubService createGitHubService() {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Retrofit retrofit =
                ArmeriaRetrofit.builder(BASE_URL)
                               .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                               .decorator(LoggingClient.newDecorator())
                               .responseTimeout(Duration.ofSeconds(10))
                               .writeTimeout(Duration.ofSeconds(10))
                               .build();
        return retrofit.create(GitHubService.class);
    }
}
