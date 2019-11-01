package com.example.service;

import java.nio.charset.StandardCharsets;

import com.linecorp.armeria.common.HttpData;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesBinary;

public class BinaryService {

    @Get("/binary")
    @ProducesBinary
    public HttpData binary() {
        return HttpData.of(StandardCharsets.UTF_8, "hello!");
    }
}
