package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(SnakeCaseStrategy.class)
@Data
public class Repo {
    private long id;
    private String nodeId;
    private String name;
    private String fullName;
    @JsonProperty("private")
    private boolean isPrivate;
    private boolean fork;
    private String htmlUrl;
    private String description;
    private String url;
    private String archiveUrl;
    private String assigneesUrl;
}
