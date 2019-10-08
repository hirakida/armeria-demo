package com.example.client.model;

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
    private List<PinpointLocation> pinpointLocations;
    private Location location;
    private Copyright copyright;
    private Description description;
}
