package com.example.client.model;

import lombok.Data;

@Data
public class Forecast {
    private String dateLabel;
    private String telop;
    private String date;
    private Temperature temperature;
    private Image image;
}
