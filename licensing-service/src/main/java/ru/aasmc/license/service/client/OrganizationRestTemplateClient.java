package ru.aasmc.license.service.client;

import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.aasmc.license.model.Organization;

@Component
public class OrganizationRestTemplateClient {
    private final KeycloakRestTemplate restTemplate;

    @Autowired
    public OrganizationRestTemplateClient(@Qualifier("keycloak") KeycloakRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Organization getOrganization(String organizationId) {
        ResponseEntity<Organization> exchange = restTemplate.exchange(
                "http://organization-service/v1/organization/{organizationId}",
                HttpMethod.GET,
                null, Organization.class, organizationId
        );
        return exchange.getBody();
    }
}
