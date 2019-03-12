package com.example.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {
    private int width;
    private String url;
    private String title;
    private int height;
}
