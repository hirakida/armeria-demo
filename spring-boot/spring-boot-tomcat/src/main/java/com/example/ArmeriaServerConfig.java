package com.example;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.server.healthcheck.HealthChecker;
import com.linecorp.armeria.server.tomcat.TomcatService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaServerConfig {
    @Bean
    public HealthChecker healthChecker(ServletWebServerApplicationContext applicationContext) {
        final Tomcat tomcat = getTomcat(applicationContext);
        return () -> tomcat.getConnector().getState().isAvailable();
    }

    @Bean
    public TomcatService tomcatService(ServletWebServerApplicationContext applicationContext) {
        final Tomcat tomcat = getTomcat(applicationContext);
        return TomcatService.of(tomcat);
    }

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(TomcatService tomcatService) {
        return sb -> sb.serviceUnder("/", tomcatService);
    }

    private static Tomcat getTomcat(ServletWebServerApplicationContext applicationContext) {
        final TomcatWebServer webServer = (TomcatWebServer) applicationContext.getWebServer();
        webServer.start();
        return webServer.getTomcat();
    }
}
