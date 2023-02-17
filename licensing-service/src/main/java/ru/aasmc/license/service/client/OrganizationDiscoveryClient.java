package ru.aasmc.license.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.aasmc.license.model.Organization;

import java.util.List;

@Component
public class OrganizationDiscoveryClient {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate discoveryTemplate;

    @Autowired
    public OrganizationDiscoveryClient(DiscoveryClient discoveryClient, RestTemplate discoveryTemplate) {
        this.discoveryClient = discoveryClient;
        this.discoveryTemplate = discoveryTemplate;
    }

    public Organization getOrganization(String organizationId) {
        List<ServiceInstance> instances = discoveryClient.getInstances("organization-service");
        if (instances.size() == 0) return null;
        String serviceUri = String.format(
                "%s/v1/organization/%s", instances.get(0).getUri().toString(),
                organizationId
        );
        ResponseEntity<Organization> restExchange = discoveryTemplate.exchange(
                serviceUri,
                HttpMethod.GET,
                null, Organization.class, organizationId
        );
        return restExchange.getBody();
    }
}
