package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.HttpStatus;
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
        final AggregatedHttpResponse res = client().get("/").aggregate().join();
        assertEquals(HttpStatus.OK, res.status());
        assertEquals("Hello, Armeria!", res.content().toStringUtf8());
    }

    @Test
    void json() {
        final AggregatedHttpResponse res = client().get("/json").aggregate().join();
        assertEquals(HttpStatus.OK, res.status());
        assertEquals("{\"message\":\"Hello!\"}", res.content().toStringUtf8());
    }

    private static WebClient client() {
        return WebClient.of(server.httpUri());
    }
}
