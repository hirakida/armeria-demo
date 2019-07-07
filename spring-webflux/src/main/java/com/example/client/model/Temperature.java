package com.example.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Temperature {
    private Detail min;
    private Detail max;

    @Data
    public static class Detail {
        private String celsius;
        private String fahrenheit;
    }
}
