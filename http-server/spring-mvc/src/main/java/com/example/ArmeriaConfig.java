package com.example;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.server.healthcheck.HealthChecker;
import com.linecorp.armeria.server.tomcat.TomcatService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaConfig {

    @Bean
    public HealthChecker tomcatConnectorHealthChecker(ServletWebServerApplicationContext applicationContext) {
        final Connector connector = getConnector(applicationContext);
        return () -> connector.getState().isAvailable();
    }

    @Bean
    public TomcatService tomcatService(ServletWebServerApplicationContext applicationContext) {
        return TomcatService.of(getConnector(applicationContext));
    }

    @Bean
    public ArmeriaServerConfigurator armeriaServiceInitializer(TomcatService tomcatService) {
        return serverBuilder -> serverBuilder.service("prefix:/", tomcatService);
    }

    private static Connector getConnector(ServletWebServerApplicationContext applicationContext) {
        final TomcatWebServer container = (TomcatWebServer) applicationContext.getWebServer();
        container.start();
        return container.getTomcat().getConnector();
    }
}
