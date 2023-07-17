package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.fasterxml.jackson.databind.JsonNode;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.ResponseEntity;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.testing.junit5.server.ServerExtension;

class MainTest {
    @RegisterExtension
    static final ServerExtension server = new ServerExtension() {
        @Override
        protected void configure(ServerBuilder sb) throws Exception {
            Main.configureServices(sb);
        }
    };

    @Test
    void hello() {
        final AggregatedHttpResponse response = WebClient.of(server.httpUri())
                                                         .get("/")
                                                         .aggregate()
                                                         .join();
        assertEquals(HttpStatus.OK, response.status());
        assertEquals("Hello, Armeria!", response.content().toStringUtf8());
    }

    @Test
    void json() {
        final ResponseEntity<JsonNode> response = RestClient.of(server.httpUri())
                                                            .get("/json")
                                                            .execute(JsonNode.class)
                                                            .join();
        assertEquals(HttpStatus.OK, response.status());
        assertEquals("{\"message\":\"Hello!\"}", response.content().toString());
    }
}
