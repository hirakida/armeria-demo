package com.example;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.thrift.CalculatorService;
import com.example.thrift.Operation;
import com.example.thrift.Work;

import com.linecorp.armeria.client.logging.LoggingRpcClient;
import com.linecorp.armeria.client.thrift.ThriftClients;
import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.thrift.ThriftFuture;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        final CalculatorService.AsyncIface calculatorService = createClient();
        final ThriftFuture<Integer> future1 = new ThriftFuture<>();
        final ThriftFuture<Integer> future2 = new ThriftFuture<>();
        calculatorService.calculate(1, new Work(1, 2, Operation.ADD), future1);
        calculatorService.calculate(2, new Work(5, 3, Operation.SUBTRACT), future2);
        future1.thenAccept(response -> logger.info("{}", response));
        future2.thenAccept(response -> logger.info("{}", response));

        TimeUnit.SECONDS.sleep(1);
    }

    private static CalculatorService.AsyncIface createClient() {
        return ThriftClients.builder("http://127.0.0.1:8080")
                            .path("/calculator")
                            .responseTimeout(Duration.ofSeconds(10))
                            .rpcDecorator(LoggingRpcClient.builder()
                                                          .requestLogLevel(LogLevel.INFO)
                                                          .successfulResponseLogLevel(LogLevel.INFO)
                                                          .newDecorator())
                            .build(CalculatorService.AsyncIface.class);
    }
}
