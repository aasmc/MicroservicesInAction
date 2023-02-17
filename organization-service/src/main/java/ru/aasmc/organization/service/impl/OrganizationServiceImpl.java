package ru.aasmc.organization.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aasmc.organization.model.Organization;
import ru.aasmc.organization.repository.OrganizationRepository;
import ru.aasmc.organization.service.OrganizationService;

import java.util.UUID;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository repository;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Organization findById(String organizationId) {
        return repository.findById(organizationId).orElse(null);
    }

    @Override
    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        ;
        organization = repository.save(organization);
        return organization;
    }

    @Override
    public void update(Organization organization) {
        repository.save(organization);
    }

    @Override
    public void delete(Organization organization) {
        repository.deleteById(organization.getId());
    }
}
