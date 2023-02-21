package ru.aasmc.license.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.aasmc.license.model.Organization;

@Repository
public interface OrganizationRedisRepository extends CrudRepository<Organization, String> {
}
