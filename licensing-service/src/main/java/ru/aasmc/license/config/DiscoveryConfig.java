package ru.aasmc.license.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DiscoveryConfig {

    @LoadBalanced
    @Bean
    public RestTemplate discoveryTemplate() {
        return new RestTemplate();
    }
}
