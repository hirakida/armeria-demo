package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.client.HttpClientBuilder;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;

@Component
public class ApiClient {
    private static final String API_URL = "http://weather.livedoor.com";
    private static final TypeReference<Map<String, Object>> TYPE_REFERENCE = new TypeReference<>() {};
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ApiClient(ObjectMapper objectMapper) {
        httpClient = new HttpClientBuilder(API_URL)
                .decorator(LoggingClient.newDecorator())
                .build();
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> getWeather(String city) {
        String uri = UriComponentsBuilder.fromPath("/forecast/webservice/json/v1")
                                         .queryParam("city", "{city}")
                                         .buildAndExpand(city)
                                         .toUriString();
        AggregatedHttpResponse response = httpClient.get(uri).aggregate().join();
        try {
            return objectMapper.readValue(response.content().toReaderUtf8(), TYPE_REFERENCE);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
