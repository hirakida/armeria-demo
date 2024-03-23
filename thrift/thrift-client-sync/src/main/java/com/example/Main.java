package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.hello.HelloRequest;
import com.example.hello.HelloResponse;
import com.example.hello.HelloService;

import com.linecorp.armeria.client.logging.LoggingRpcClient;
import com.linecorp.armeria.client.thrift.ThriftClients;
import com.linecorp.armeria.common.SerializationFormat;
import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.thrift.ThriftSerializationFormats;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        final HelloService.Iface client1 = createClient(ThriftSerializationFormats.BINARY);
        logger.info("{}", client1.hello1("tbinary"));

        final HelloService.Iface client2 = createClient(ThriftSerializationFormats.COMPACT);
        logger.info("{}", client2.hello1("tcompact"));

        final HelloService.Iface client3 = createClient(ThriftSerializationFormats.JSON);
        logger.info("{}", client3.hello1("tjson"));

        final HelloService.Iface client4 = createClient(ThriftSerializationFormats.TEXT);
        logger.info("{}", client4.hello1("ttext"));

        final HelloResponse response2 = client1.hello2(new HelloRequest("hirakida"));
        logger.info("{}", response2);
    }

    private static HelloService.Iface createClient(SerializationFormat format) {
        return ThriftClients.builder("http://localhost:8080")
                            .path("/hello")
                            .serializationFormat(format)
                            .rpcDecorator(LoggingRpcClient.builder()
                                                          .requestLogLevel(LogLevel.INFO)
                                                          .successfulResponseLogLevel(LogLevel.INFO)
                                                          .newDecorator())
                            .build(HelloService.Iface.class);
    }
}
