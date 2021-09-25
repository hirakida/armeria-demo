package com.example;

import java.time.Duration;

import org.apache.thrift.TException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.hello.Hello;
import com.example.thrift.Calculator;
import com.example.thrift.Operation;
import com.example.thrift.Work;

import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.client.logging.LoggingRpcClient;
import com.linecorp.armeria.common.logging.LogLevel;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        calculator();
        hello();
    }

    private static void calculator() throws TException {
        Calculator.Iface client =
                Clients.builder("tbinary+http://127.0.0.1:8080/calculator")
                       .responseTimeout(Duration.ofSeconds(10))
                       .rpcDecorator(LoggingRpcClient.builder()
                                                     .requestLogLevel(LogLevel.INFO)
                                                     .successfulResponseLogLevel(LogLevel.INFO)
                                                     .newDecorator())
                       .build(Calculator.Iface.class);
        int result = client.add(1, 2);
        log.info("tbinary {}", result);

        result = client.calculate(1, new Work(5, 3, Operation.SUBTRACT));
        log.info("tbinary {}", result);
    }

    private static void hello() throws TException {
        Hello.Iface client1 = buildHelloClient("tbinary");
        log.info("{}", client1.hello("tbinary"));

        Hello.Iface client2 = buildHelloClient("tcompact");
        log.info("{}", client2.hello("tcompact"));

        Hello.Iface client3 = buildHelloClient("tjson");
        log.info("{}", client3.hello("tjson"));

        Hello.Iface client4 = buildHelloClient("ttext");
        log.info("{}", client4.hello("ttext"));
    }

    private static Hello.Iface buildHelloClient(String format) {
        return Clients.builder(format + "+http://127.0.0.1:8080/hello")
                      .responseTimeout(Duration.ofSeconds(10))
                      .rpcDecorator(LoggingRpcClient.builder()
                                                    .requestLogLevel(LogLevel.INFO)
                                                    .successfulResponseLogLevel(LogLevel.INFO)
                                                    .newDecorator())
                      .build(Hello.Iface.class);
    }
}
