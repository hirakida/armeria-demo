package com.example;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.metric.MetricCollectingClient;
import com.linecorp.armeria.common.metric.MeterIdPrefixFunction;
import com.linecorp.armeria.common.prometheus.PrometheusMeterRegistries;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.metric.MetricCollectingService;
import com.linecorp.armeria.server.prometheus.PrometheusExpositionService;

import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;

public final class Main {
    public static void main(String[] args) {
        final PrometheusMeterRegistry meterRegistry = PrometheusMeterRegistries.defaultRegistry();
        final RestClient restClient = createRestClient(meterRegistry);

        final Server server =
                Server.builder()
                      .http(8080)
                      .accessLogWriter(AccessLogWriter.combined(), false)
                      .decorator(LoggingService.newDecorator())
                      .decorator(MetricCollectingService.newDecorator(
                              MeterIdPrefixFunction.ofDefault("http.server")))
                      .meterRegistry(meterRegistry)
                      .serviceUnder("/internal/docs",
                                    DocService.builder()
                                              .examplePaths(GitHubService.class,
                                                            "getUser",
                                                            "/github/hirakida")
                                              .examplePaths(HttpStatusService.class,
                                                            "getStatusCode",
                                                            "/status/200", "/status/201")
                                              .build())
                      .service("/internal/metrics",
                               PrometheusExpositionService.of(meterRegistry.getPrometheusRegistry()))
                      .annotatedService(new HttpStatusService())
                      .annotatedService(new GitHubService(restClient))
                      .build();
        server.start().join();
    }

    private static RestClient createRestClient(PrometheusMeterRegistry meterRegistry) {
        return RestClient.builder("https://api.github.com")
                         .decorator(LoggingClient.newDecorator())
                         .decorator(MetricCollectingClient.newDecorator(
                                 MeterIdPrefixFunction.ofDefault("http.client")
                                                      .withTags("service", "GitHubClient")))
                         .factory(ClientFactory.builder()
                                               .meterRegistry(meterRegistry)
                                               .build())
                         .build();
    }
}
