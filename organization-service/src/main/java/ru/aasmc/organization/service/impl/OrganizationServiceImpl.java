package ru.aasmc.organization.service.impl;

import brave.ScopedSpan;
import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aasmc.organization.events.source.SimpleSourceBean;
import ru.aasmc.organization.model.Organization;
import ru.aasmc.organization.repository.OrganizationRepository;
import ru.aasmc.organization.service.OrganizationService;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    private final OrganizationRepository repository;
    private final SimpleSourceBean simpleSourceBean;
    private final Tracer tracer;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository repository, SimpleSourceBean simpleSourceBean, Tracer tracer) {
        this.repository = repository;
        this.simpleSourceBean = simpleSourceBean;
        this.tracer = tracer;
    }

    @Override
    public Organization findById(String organizationId) {
        ScopedSpan newSpan = tracer.startScopedSpan("getOrgDBCall");
        Optional<Organization> opt = null;
        try {
            opt = repository.findById(organizationId);
            simpleSourceBean.publishOrganizationChange("GET", organizationId);
            if (!opt.isPresent()) {
                String message = String.format("Unable to find an organization with the Organization id %s", organizationId);
                logger.error(message);
                throw new IllegalArgumentException(message);
            }
            logger.debug("Retrieving Organization Info: " + opt.get().toString());
        } finally {
            newSpan.tag("peer.service", "postgres");
            newSpan.annotate("Client received");
            newSpan.finish();
        }
        return opt.orElse(null);
    }

    @Override
    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = repository.save(organization);
        simpleSourceBean.publishOrganizationChange("SAVE", organization.getId());
        return organization;
    }

    @Override
    public void update(Organization organization) {
        repository.save(organization);
        simpleSourceBean.publishOrganizationChange("UPDATE", organization.getId());
    }

    @Override
    public void delete(Organization organization) {
        repository.deleteById(organization.getId());
        simpleSourceBean.publishOrganizationChange("DELETE", organization.getId());
    }
}
