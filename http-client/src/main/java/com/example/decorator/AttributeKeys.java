package com.example.decorator;

import java.time.LocalDateTime;

import io.netty.util.AttributeKey;

public interface AttributeKeys {
    AttributeKey<LocalDateTime> DATETIME_ATTR = AttributeKey.valueOf(LocalDateTime.class, "DATETIME_ATTR");
    AttributeKey<String> USERNAME_ATTR = AttributeKey.valueOf(String.class, "USERNAME_ATTR");
}
