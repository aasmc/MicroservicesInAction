package ru.aasmc.license.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import ru.aasmc.license.utils.UserContextInterceptor;

import java.util.Collections;
import java.util.List;

@Configuration
public class DiscoveryConfig {

    @Qualifier("loadBalanced")
    @LoadBalanced
    @Bean
    public RestTemplate loadBalancedTemplate() {
        RestTemplate template = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
        interceptors.add(new UserContextInterceptor());
        template.setInterceptors(interceptors);
        return template;
    }

    @Bean
    @Qualifier("discovery")
    public RestTemplate discoveryTemplate() {
        return new RestTemplate();
    }

}
