package com.example;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.file.HttpFile;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.websocket.WebSocketService;

public final class Main {
    public static void main(String[] args) {
        final Server server =
                Server.builder()
                      .http(8080)
                      .decorator(LoggingService.newDecorator())
                      .service("/ws", WebSocketService.of(new WebSocketServiceHandlerImpl()))
                      .service("/", HttpFile.of(Main.class.getClassLoader(), "index.html")
                                            .asService())
                      .build();
        server.start().join();
    }
}
