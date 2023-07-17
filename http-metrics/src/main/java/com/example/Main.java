package com.example;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.metric.MetricCollectingClient;
import com.linecorp.armeria.common.metric.MeterIdPrefixFunction;
import com.linecorp.armeria.common.metric.PrometheusMeterRegistries;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.metric.MetricCollectingService;
import com.linecorp.armeria.server.metric.PrometheusExpositionService;

import io.micrometer.prometheus.PrometheusMeterRegistry;

public final class Main {

    public static void main(String[] args) {
        final PrometheusMeterRegistry meterRegistry = PrometheusMeterRegistries.defaultRegistry();
        final RestClient restClient =
                RestClient.builder("https://api.github.com")
                          .decorator(LoggingClient.newDecorator())
                          .decorator(MetricCollectingClient.newDecorator(
                                  MeterIdPrefixFunction.ofDefault("http.client")
                                                       .withTags("service", "githubClient")))
                          .factory(ClientFactory.builder()
                                                .meterRegistry(meterRegistry)
                                                .build())
                          .build();
        final Server server = Server.builder()
                                    .http(8080)
                                    .accessLogWriter(AccessLogWriter.combined(), false)
                                    .decorator(LoggingService.newDecorator())
                                    .decorator(MetricCollectingService.newDecorator(
                                            MeterIdPrefixFunction.ofDefault("http.service")))
                                    .meterRegistry(meterRegistry)
                                    .serviceUnder("/docs", new DocService())
                                    .service("/metrics",
                                             PrometheusExpositionService.of(
                                                     meterRegistry.getPrometheusRegistry()))
                                    .annotatedService(new HelloService())
                                    .annotatedService(new GitHubService(restClient))
                                    .build();
        server.start().join();
    }
}
