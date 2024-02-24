package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.testing.junit5.server.ServerExtension;

class HelloServiceTest {
    @RegisterExtension
    static final ServerExtension server = new ServerExtension() {
        @Override
        protected void configure(ServerBuilder sb) throws Exception {
            sb.annotatedService(new HelloService());
        }
    };

    @Test
    void hello1() {
        final WebClient client = client();
        final AggregatedHttpResponse response = client.get("/hello1").aggregate().join();
        Assertions.assertEquals("Hello!", response.contentUtf8());
    }

    @Test
    void hello2() {
        final WebClient client = client();
        final AggregatedHttpResponse response = client.get("/hello2").aggregate().join();
        Assertions.assertEquals("Hello!!", response.contentUtf8());
    }

    private static WebClient client() {
        return WebClient.of(server.httpUri());
    }
}
