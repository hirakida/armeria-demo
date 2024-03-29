package com.example;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.file.FileService;
import com.linecorp.armeria.server.logging.LoggingService;

public final class Main {
    public static void main(String[] args) {
        final Server server =
                Server.builder()
                      .http(8081)
                      .decorator(LoggingService.newDecorator())
                      .serviceUnder("/",
                                    FileService.of(ClassLoader.getSystemClassLoader(),
                                                   "/static"))
                      .build();
        server.start().join();
    }
}
