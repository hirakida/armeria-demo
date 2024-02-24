package com.example;

import org.eclipse.jetty.server.Server;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.jetty.JettyWebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.server.healthcheck.HealthChecker;
import com.linecorp.armeria.server.jetty.JettyService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaServerConfig {
    // https://github.com/spring-projects/spring-boot/blob/3.2.x/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/servlet/ServletWebServerFactoryConfiguration.java#L91-L96
    @Bean
    public JettyServletWebServerFactory jettyServletWebServerFactory(
            ObjectProvider<JettyServerCustomizer> serverCustomizers) {
        final JettyServletWebServerFactory factory = new JettyServletWebServerFactory() {
            @Override
            protected JettyWebServer getJettyWebServer(Server server) {
                return new JettyWebServer(server, true);
            }
        };
        factory.getServerCustomizers().addAll(serverCustomizers.orderedStream().toList());
        return factory;
    }

    @Bean
    public HealthChecker healthChecker(ServletWebServerApplicationContext applicationContext) {
        final Server server = getJettyServer(applicationContext);
        return server::isRunning;
    }

    @Bean
    public JettyService jettyService(ServletWebServerApplicationContext applicationContext) {
        return JettyService.of(getJettyServer(applicationContext));
    }

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(JettyService jettyService) {
        return sb -> sb.serviceUnder("/", jettyService);
    }

    private static Server getJettyServer(ServletWebServerApplicationContext applicationContext) {
        final JettyWebServer jettyWebServer = (JettyWebServer) applicationContext.getWebServer();
        return jettyWebServer.getServer();
    }
}
