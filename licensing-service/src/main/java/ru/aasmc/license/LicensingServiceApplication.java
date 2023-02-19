package ru.aasmc.license;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import ru.aasmc.license.model.License;
import ru.aasmc.license.repository.LicenseRepository;

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

    @Bean
    public CommandLineRunner commandLineRunner(LicenseRepository repository) {
        return args -> {
            repository.save(new License("f2a9c9d4-d2c0-44fa-97fe-724d77173c62", "d898a142-de44-466c-8c88-9ceb2c2429d3", "Software Product", "Ostock", "complete", "I AM DEV", "org name", "contact name", "contact phone", "contact email"));
            repository.save(new License("279709ff-e6d5-4a54-8b55-a5c37542025b", "e839ee96-28de-4f67-bb79-870ca89743a0", "Software Product", "Ostock", "complete", "I AM DEV", "org name", "contact name", "contact phone", "contact email"));
        };
    }
}
