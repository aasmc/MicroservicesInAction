package ru.aasmc.organization.service.impl;

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
    private final OrganizationRepository repository;
    private final SimpleSourceBean simpleSourceBean;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository repository, SimpleSourceBean simpleSourceBean) {
        this.repository = repository;
        this.simpleSourceBean = simpleSourceBean;
    }

    @Override
    public Organization findById(String organizationId) {
        Optional<Organization> opt = repository.findById(organizationId);
        simpleSourceBean.publishOrganizationChange("GET", organizationId);
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
