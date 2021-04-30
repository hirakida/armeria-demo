package com.example;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.server.tomcat.TomcatService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaServerConfig {
    @Bean
    public TomcatService tomcatService(ServletWebServerApplicationContext applicationContext) {
        final TomcatWebServer webServer = (TomcatWebServer) applicationContext.getWebServer();
        webServer.start();
        Connector connector = webServer.getTomcat().getConnector();
        return TomcatService.of(connector);
    }

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(TomcatService tomcatService) {
        return builder -> builder.service("prefix:/", tomcatService);
    }
}
