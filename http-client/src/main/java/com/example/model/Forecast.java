package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
    private String dateLabel;
    private String telop;
    private String date;
    private Temperature temperature;
    private Image image;
}
