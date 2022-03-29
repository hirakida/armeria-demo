package com.example;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.graphql.GraphqlService;
import com.linecorp.armeria.server.logging.AccessLogWriter;

public final class Main {

    public static void main(String[] args) {
        final GraphqlService service =
                GraphqlService.builder()
                              .runtimeWiring(builder -> {
                                  builder.type("Query",
                                               typeWiring -> typeWiring.dataFetcher("hello",
                                                                                    new HelloDataFetcher()));
                              })
                              .build();
        final Server server =
                Server.builder()
                      .http(8080)
                      .service("/graphql", service)
                      .serviceUnder("/docs", DocService.builder().build())
                      .accessLogWriter(AccessLogWriter.combined(), false)
                      .build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop().join()));
        server.start().join();
    }
}
