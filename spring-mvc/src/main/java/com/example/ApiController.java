package com.example;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    private final ApiClient apiClient;
    private final MessageDigest md5;

    public ApiController(ApiClient apiClient) throws NoSuchAlgorithmException {
        this.apiClient = apiClient;
        md5 = MessageDigest.getInstance("MD5");
    }

    @GetMapping("/weather")
    public Map<String, Object> getWeather(@RequestParam(defaultValue = "400010") String city) {
        return apiClient.getWeather(city);
    }

    @GetMapping("/md5/{text}")
    public String getMd5(@PathVariable String text) {
        byte[] bytes = md5.digest(text.getBytes(StandardCharsets.UTF_8));
        return DigestUtils.md5DigestAsHex(bytes);
    }
}
