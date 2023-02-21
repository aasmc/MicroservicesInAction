package ru.aasmc.license;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.aasmc.license.events.model.OrganizationChangeModel;

@SpringBootApplication
/**
 * This annotation enables the microservice to refresh properties at runtime
 * using Spring Boot Actuator /refresh endpoint
 * This annotation only
 * reloads the custom Spring properties you have in your application configuration.
 * Items like your database configuration used by Spring Data won’t be reloaded by this
 * annotation.
 */
@RefreshScope
@EnableFeignClients
@EnableDiscoveryClient
@EnableEurekaClient
public class LicensingServiceApplication {

    final Logger log = LoggerFactory.getLogger(LicensingServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LicensingServiceApplication.class, args);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setBasenames("messages");
        return messageSource;
    }
}
