package com.example.client.model;

import lombok.Data;

@Data
public class Temperature {
    private Detail min;
    private Detail max;

    @Data
    public static class Detail {
        private String celsius;
        private String fahrenheit;
    }
}
