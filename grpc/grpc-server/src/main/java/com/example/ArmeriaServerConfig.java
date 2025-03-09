package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.Calculator.CalculatorRequest;
import com.example.Calculator.CalculatorRequest.OperationType;
import com.example.Hello.HelloRequest;
import com.example.interceptor.ServerInterceptorImpl;
import com.example.service.CalculatorService;
import com.example.service.HelloService;

import com.linecorp.armeria.common.HttpHeaderNames;
import com.linecorp.armeria.common.HttpMethod;
import com.linecorp.armeria.common.grpc.GrpcMeterIdPrefixFunction;
import com.linecorp.armeria.common.grpc.protocol.GrpcHeaderNames;
import com.linecorp.armeria.common.metric.MeterIdPrefixFunction;
import com.linecorp.armeria.common.prometheus.PrometheusMeterRegistries;
import com.linecorp.armeria.server.cors.CorsService;
import com.linecorp.armeria.server.cors.CorsServiceBuilder;
import com.linecorp.armeria.server.docs.DocServiceFilter;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import com.linecorp.armeria.spring.DocServiceConfigurator;

import io.grpc.ServerInterceptors;
import io.grpc.protobuf.services.ProtoReflectionServiceV1;
import io.grpc.reflection.v1alpha.ServerReflectionGrpc;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;

@Configuration
public class ArmeriaServerConfig {
    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(CalculatorService calculatorService,
                                                               HelloService helloService,
                                                               MeterRegistry meterRegistry) {
        final GrpcService grpcService =
                GrpcService.builder()
                           .addService(calculatorService)
                           .addService(ServerInterceptors.intercept(helloService,
                                                                    new ServerInterceptorImpl()))
                           .addService(ProtoReflectionServiceV1.newInstance())
                           .enableUnframedRequests(true)
                           .build();
        // For gRPC-Web
        final CorsServiceBuilder corsServiceBuilder =
                CorsService.builder("http://localhost:8081")
                           .allowRequestMethods(HttpMethod.POST)
                           .allowRequestHeaders(HttpHeaderNames.CONTENT_TYPE,
                                                HttpHeaderNames.of("X-USER-AGENT"),
                                                HttpHeaderNames.of("X-GRPC-WEB"))
                           .exposeHeaders(GrpcHeaderNames.GRPC_STATUS,
                                          GrpcHeaderNames.GRPC_MESSAGE,
                                          GrpcHeaderNames.ARMERIA_GRPC_THROWABLEPROTO_BIN);

        return builder -> builder.service(grpcService)
                                 .decorator(corsServiceBuilder.newDecorator())
                                 .decorator(LoggingService.newDecorator())
                                 .accessLogWriter(AccessLogWriter.combined(), false)
                                 .meterRegistry(meterRegistry);
    }

    @Bean
    public DocServiceConfigurator docServiceConfigurator() {
        final CalculatorRequest calculatorRequest =
                CalculatorRequest.newBuilder()
                                 .setNumber1(2)
                                 .setNumber2(3)
                                 .setOperation(OperationType.MULTIPLY)
                                 .build();
        final HelloRequest helloRequest = HelloRequest.newBuilder()
                                                      .setName("hirakida")
                                                      .build();
        return builder -> builder.exampleRequests(CalculatorServiceGrpc.SERVICE_NAME,
                                                  "Calculate",
                                                  calculatorRequest)
                                 .exampleRequests(HelloServiceGrpc.SERVICE_NAME,
                                                  "HelloUnary",
                                                  helloRequest)
                                 .exclude(DocServiceFilter.ofServiceName(ServerReflectionGrpc.SERVICE_NAME));
    }

    @Bean
    public MeterIdPrefixFunction meterIdPrefixFunction() {
        return GrpcMeterIdPrefixFunction.of("grpc.service");
    }

    @Bean
    public PrometheusMeterRegistry meterRegistry() {
        return PrometheusMeterRegistries.defaultRegistry();
    }
}
