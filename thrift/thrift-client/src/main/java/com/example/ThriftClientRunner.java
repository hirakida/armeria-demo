package com.example;

import java.util.concurrent.TimeUnit;

import org.apache.thrift.async.AsyncMethodCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.hello.HelloRequest;
import com.example.hello.HelloResponse;
import com.example.hello.HelloService;
import com.example.thrift.Calculator;
import com.example.thrift.Operation;
import com.example.thrift.Work;

import com.linecorp.armeria.client.logging.LoggingRpcClient;
import com.linecorp.armeria.client.thrift.ThriftClients;
import com.linecorp.armeria.common.SerializationFormat;
import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.thrift.ThriftSerializationFormats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ThriftClientRunner implements CommandLineRunner {
    private final Calculator.AsyncIface calculatorClient;

    @Override
    public void run(String... args) throws Exception {
        final AsyncMethodCallback<Integer> resultHandler = new AsyncMethodCallbackImpl<>();
        calculatorClient.add(1, 2, resultHandler);
        calculatorClient.calculate(1, new Work(5, 3, Operation.SUBTRACT), resultHandler);
        TimeUnit.SECONDS.sleep(1);

        final HelloService.Iface client1 = buildClient(ThriftSerializationFormats.BINARY);
        log.info("{}", client1.hello1("tbinary"));
        final HelloService.Iface client2 = buildClient(ThriftSerializationFormats.COMPACT);
        log.info("{}", client2.hello1("tcompact"));
        final HelloService.Iface client3 = buildClient(ThriftSerializationFormats.JSON);
        log.info("{}", client3.hello1("tjson"));
        final HelloService.Iface client4 = buildClient(ThriftSerializationFormats.TEXT);
        log.info("{}", client4.hello1("ttext"));

        final HelloResponse response2 = client1.hello2(new HelloRequest("hirakida"));
        log.info("{}", response2);
    }

    private static HelloService.Iface buildClient(SerializationFormat format) {
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
