package com.example;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(SnakeCaseStrategy.class)
@Data
public class User {
    private long id;
    private String login;
    private String name;
    private String url;
    private long publicRepos;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
