package ru.aasmc.license.service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.aasmc.license.model.Organization;
import ru.aasmc.license.repository.OrganizationRedisRepository;
import ru.aasmc.license.utils.UserContext;

@Component
public class OrganizationRestTemplateClient {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);
    private final RestTemplate restTemplate;
    private final OrganizationRedisRepository redisRepository;

    @Autowired
    public OrganizationRestTemplateClient(RestTemplate restTemplate,
                                          OrganizationRedisRepository redisRepository) {
        this.restTemplate = restTemplate;
        this.redisRepository = redisRepository;
    }

    public Organization getOrganization(String organizationId) {
        logger.debug("In Licensing Service getOrganization: {}", UserContext.getCorrelationId());

        Organization organization = checkRedisCache(organizationId);
        if (organization != null) {
            logger.debug("Successfully retrieved an organization {} from the redis cache: {}",
                    organizationId, organization);
            return organization;
        }
        logger.debug("Unable to locate organization from the redis cache: {}", organizationId);

        ResponseEntity<Organization> exchange = restTemplate.exchange(
                "http://gateway-server:8072/organization/v1/organization/{organizationId}",
                HttpMethod.GET,
                null, Organization.class, organizationId
        );
        organization = exchange.getBody();
        if (organization != null) {
            cacheOrganization(organization);
        }
        return organization;
    }

    private Organization checkRedisCache(String organizationId) {
        try {
            return redisRepository.findById(organizationId).orElse(null);
        } catch (Exception e) {
            logger.error("Error encountered while trying to retrieve organization {} " +
                    ". Method: checkRedisCache(). Exception {}", organizationId, e.getMessage());
            return null;
        }
    }

    private void cacheOrganization(Organization organization) {
        try {
            redisRepository.save(organization);
        } catch (Exception e) {
            logger.error("Unable to cache organization {} in Redis. Exception {}",
                    organization.getId(), e.getMessage());
        }
    }
}
