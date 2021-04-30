package com.example;

import java.time.LocalDateTime;

import lombok.Value;

@Value
public class HelloResponse {
    String message;
    LocalDateTime dateTime;
}
