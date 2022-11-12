package com.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
@Builder
@Jacksonized
public class User {
    long id;
    String login;
    String name;
}
