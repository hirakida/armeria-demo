package com.example;

import com.linecorp.armeria.common.metric.MeterIdPrefixFunction;
import com.linecorp.armeria.common.metric.PrometheusMeterRegistries;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.metric.MetricCollectingService;
import com.linecorp.armeria.server.metric.PrometheusExpositionService;

import io.micrometer.prometheus.PrometheusMeterRegistry;

public class Main {

    public static void main(String[] args) {
        PrometheusMeterRegistry meterRegistry = PrometheusMeterRegistries.defaultRegistry();
        Server server = Server.builder()
                              .http(8080)
                              .decorator(LoggingService.newDecorator())
                              .accessLogWriter(AccessLogWriter.combined(), false)
                              .meterRegistry(meterRegistry)
                              .service("/metrics",
                                       PrometheusExpositionService.of(meterRegistry.getPrometheusRegistry()))
                              .decorator(MetricCollectingService.newDecorator(
                                      MeterIdPrefixFunction.ofDefault("http.service")))
                              .annotatedService(new HelloService())
                              .build();
        server.start().join();
    }
}
