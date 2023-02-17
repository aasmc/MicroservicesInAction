package ru.aasmc.license;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

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
public class LicensingServiceApplication {
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
