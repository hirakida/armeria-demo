package com.example.client.model;

import java.util.List;

import lombok.Data;

@Data
public class Copyright {
    private List<Provider> provider;
    private String link;
    private String title;
    private Image image;

    @Data
    public static class Provider {
        private String link;
        private String name;
    }
}
