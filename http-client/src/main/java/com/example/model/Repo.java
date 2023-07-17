package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(SnakeCaseStrategy.class)
public record Repo(long id,
                   String nodeId,
                   String name,
                   String fullName,
                   @JsonProperty("private") boolean isPrivate,
                   boolean fork,
                   String htmlUrl,
                   String description,
                   String url,
                   String archiveUrl,
                   String assigneesUrl) {
}
