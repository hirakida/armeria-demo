package com.example;

import com.linecorp.armeria.common.reactor3.RequestContextHooks;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.logging.LoggingService;

public class Main {
    public static void main(String[] args) {
        RequestContextHooks.enable();
        final Server server = Server.builder()
                                    .http(8080)
                                    .decorator(LoggingService.newDecorator())
                                    .annotatedService(new ReactorService())
                                    .build();
        server.start().join();
    }
}
