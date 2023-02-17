package ru.aasmc.organization.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.aasmc.organization.model.Organization;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {
}
