package com.example;

import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.logging.LogWriter;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;

public final class Main {
    public static void main(String[] args) {
        final Server server =
                Server.builder()
                      .http(8080)
//                      .accessLogWriter(AccessLogWriter.combined(), false)
                      .serviceUnder("/docs", new DocService())
                      .decorator(delegate -> LoggingService
                              .builder()
                              .successSamplingRate(0.5f)
                              .failureSamplingRate(1.0f)
                              .logWriter(LogWriter.builder()
                                                  .requestLogLevel(LogLevel.INFO)
                                                  .successfulResponseLogLevel(LogLevel.INFO)
                                                  .failureResponseLogLevel(LogLevel.WARN)
                                                  .build())
                              .build(delegate))
                      .annotatedService(new HelloService())
                      .build();
        server.start().join();
    }
}
