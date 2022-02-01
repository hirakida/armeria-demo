package com.example;

import java.util.List;

import org.apache.thrift.TException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.example.hello.HelloRequest;
import com.example.hello.HelloResponse;
import com.example.hello.HelloService;

import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.common.thrift.ThriftFuture;
import com.linecorp.armeria.common.thrift.ThriftSerializationFormats;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.thrift.THttpService;
import com.linecorp.armeria.testing.junit5.server.ServerExtension;

public class HelloServiceImplTest {
    @RegisterExtension
    static final ServerExtension server = new ServerExtension() {
        @Override
        protected void configure(ServerBuilder sb) throws Exception {
            sb.route()
              .path("/hello")
              .defaultServiceName("Hello")
              .build(THttpService.of(new HelloServiceImpl()));
        }
    };

    @Test
    void hello1() throws TException {
        final ThriftFuture<String> future = new ThriftFuture<>();
        client().hello1("hirakida", future);
        Assertions.assertEquals("Hello, hirakida!", future.join());
    }

    @Test
    void hello2() throws TException {
        final ThriftFuture<HelloResponse> future = new ThriftFuture<>();
        client().hello2(new HelloRequest("hirakida"), future);
        Assertions.assertEquals(List.of("Hello, hirakida!"), future.join().getMessages());
    }

    private static HelloService.AsyncIface client() {
        final String uri = server.httpUri(ThriftSerializationFormats.BINARY) + "/hello";
        return Clients.newClient(uri, HelloService.AsyncIface.class);
    }
}
