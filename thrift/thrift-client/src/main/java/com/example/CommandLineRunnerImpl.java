package com.example;

import static com.example.ArmeriaClientConfig.buildHelloClient;

import org.apache.thrift.TException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.hello.Hello;
import com.example.hello.HelloRequest;
import com.example.thrift.Calculator;
import com.example.thrift.Operation;
import com.example.thrift.Work;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final Calculator.Iface calculatorClient;

    @Override
    public void run(String... args) throws Exception {
        calculator();
        hello();
    }

    private void calculator() throws TException {
        int result = calculatorClient.add(1, 2);
        log.info("{}", result);

        result = calculatorClient.calculate(1, new Work(5, 3, Operation.SUBTRACT));
        log.info("{}", result);
    }

    private static void hello() throws TException {
        Hello.Iface client1 = buildHelloClient("tbinary");
        log.info("{}", client1.hello1("tbinary"));
        log.info("{}", client1.hello2(new HelloRequest("hirakida")));

        Hello.Iface client2 = buildHelloClient("tcompact");
        log.info("{}", client2.hello1("tcompact"));

        Hello.Iface client3 = buildHelloClient("tjson");
        log.info("{}", client3.hello1("tjson"));

        Hello.Iface client4 = buildHelloClient("ttext");
        log.info("{}", client4.hello1("ttext"));
    }
}
