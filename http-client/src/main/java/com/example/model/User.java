package com.example.model;

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
    private String gravatarId;
    private String login;
    private String name;
    private String avatarUrl;
    private String url;
    private String htmlUrl;
    private String followersUrl;
    private String followingUrl;
    private String gistsUrl;
    private String starredUrl;
    private String subscriptionsUrl;
    private String organizationsUrl;
    private String reposUrl;
    private String eventsUrl;
    private String receivedEventsUrl;
    private String type;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String bio;
    private boolean siteAdmin;
    private boolean hireable;
    private long publicRepos;
    private long publicGists;
    private long followers;
    private long following;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
