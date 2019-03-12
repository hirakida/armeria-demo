package com.example.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;

@Component
public class MessageDigestService {
    private final MessageDigest md5;

    public MessageDigestService() throws NoSuchAlgorithmException {
        md5 = MessageDigest.getInstance("MD5");
    }

    @Get("/md5/{text}")
    public String md5(@Param String text) {
        byte[] bytes = md5.digest(text.getBytes(StandardCharsets.UTF_8));
        return DigestUtils.md5DigestAsHex(bytes);
    }
}
