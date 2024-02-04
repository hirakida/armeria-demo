package com.example;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import com.example.thrift.CalculatorService;
import com.example.thrift.Operation;
import com.example.thrift.Work;

import com.linecorp.armeria.client.logging.LoggingRpcClient;
import com.linecorp.armeria.client.thrift.ThriftClients;
import com.linecorp.armeria.common.logging.LogLevel;

public final class Main {
    public static void main(String[] args) throws Exception {
        final CalculatorService.AsyncIface calculatorService =
                ThriftClients.builder("http://127.0.0.1:8080")
                             .path("/calculator")
                             .responseTimeout(Duration.ofSeconds(10))
                             .rpcDecorator(LoggingRpcClient.builder()
                                                           .requestLogLevel(LogLevel.INFO)
                                                           .successfulResponseLogLevel(LogLevel.INFO)
                                                           .newDecorator())
                             .build(CalculatorService.AsyncIface.class);

        final AsyncMethodCallbackImpl<Integer> resultHandler = new AsyncMethodCallbackImpl<>();
        calculatorService.calculate(1, new Work(1, 2, Operation.ADD), resultHandler);
        calculatorService.calculate(2, new Work(5, 3, Operation.SUBTRACT), resultHandler);
        TimeUnit.SECONDS.sleep(1);
    }
}
