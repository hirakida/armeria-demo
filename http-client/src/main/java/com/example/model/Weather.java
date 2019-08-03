package com.example.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    private String link;
    private String title;
    private String publicTime;
    private List<Forecast> forecasts;
    private Location location;
}
