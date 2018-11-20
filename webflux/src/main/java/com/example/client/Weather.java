package com.example.client;

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

    @Data
    public static class Forecast {
        private String dateLabel;
        private String telop;
        private String date;
        private Temperature temperature;
        private Image image;

        @Data
        public static class Temperature {
            private Detail min;
            private Detail max;

            @Data
            public static class Detail {
                private String celsius;
                private String fahrenheit;
            }
        }

        @Data
        public static class Image {
            private int width;
            private String url;
            private String title;
            private int height;
        }
    }

    @Data
    public static class Location {
        private String city;
        private String area;
        private String prefecture;
    }
}
