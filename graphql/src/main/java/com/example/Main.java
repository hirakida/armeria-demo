package com.example;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.graphql.GraphqlService;
import com.linecorp.armeria.server.logging.AccessLogWriter;

public class Main {

    public static void main(String[] args) {
        GraphqlService graphqlService =
                GraphqlService.builder()
                              .runtimeWiring(builder -> {
                                  builder.type("Query",
                                               typeWiring -> typeWiring.dataFetcher("hello",
                                                                                    new HelloDataFetcher()));
                              })
                              .build();
        Server server = Server.builder()
                              .http(8080)
                              .service("/graphql", graphqlService)
                              .accessLogWriter(AccessLogWriter.combined(), false)
                              .build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop().join()));
        server.start().join();
    }
}
