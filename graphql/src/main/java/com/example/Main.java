package com.example;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.graphql.GraphqlService;
import com.linecorp.armeria.server.logging.AccessLogWriter;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

public final class Main {
    public static void main(String[] args) {
        final GraphqlService service =
                GraphqlService.builder()
                              .graphql(GraphQL.newGraphQL(createGraphQLSchema()).build())
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

    private static GraphQLSchema createGraphQLSchema() {
        final SchemaParser schemaParser = new SchemaParser();
        final SchemaGenerator schemaGenerator = new SchemaGenerator();
        final TypeDefinitionRegistry typeDefinitionRegistry = new TypeDefinitionRegistry();
        typeDefinitionRegistry.merge(schemaParser.parse(loadSchema()));

        final RuntimeWiring runtimeWiring =
                RuntimeWiring.newRuntimeWiring()
                             .type("Query",
                                   typeWiring -> typeWiring.dataFetcher("hello", new HelloDataFetcher()))
                             .build();
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }

    private static File loadSchema() {
        final ClassLoader classLoader = Main.class.getClassLoader();
        final URL url = classLoader.getResource("schema.graphqls");
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
