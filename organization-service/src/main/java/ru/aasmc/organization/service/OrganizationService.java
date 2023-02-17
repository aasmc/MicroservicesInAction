package ru.aasmc.organization.service;

import ru.aasmc.organization.model.Organization;

public interface OrganizationService {
    Organization findById(String organizationId);

    Organization create(Organization organization);

    void update(Organization organization);

    void delete(Organization organization);
}
