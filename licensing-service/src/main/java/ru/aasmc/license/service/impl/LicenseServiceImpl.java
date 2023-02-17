package ru.aasmc.license.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.aasmc.license.config.ServiceConfig;
import ru.aasmc.license.model.License;
import ru.aasmc.license.model.Organization;
import ru.aasmc.license.repository.LicenseRepository;
import ru.aasmc.license.service.LicenseService;
import ru.aasmc.license.service.client.OrganizationDiscoveryClient;
import ru.aasmc.license.service.client.OrganizationFeignClient;
import ru.aasmc.license.service.client.OrganizationRestTemplateClient;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseServiceImpl implements LicenseService {

    private final MessageSource messages;
    private final LicenseRepository licenseRepository;
    private final ServiceConfig config;

    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    @Autowired
    private OrganizationRestTemplateClient organizationRestTemplateClient;

    @Autowired
    private OrganizationDiscoveryClient organizationDiscoveryClient;

    @Autowired
    public LicenseServiceImpl(MessageSource messages,
                              LicenseRepository licenseRepository,
                              ServiceConfig config) {
        this.messages = messages;
        this.licenseRepository = licenseRepository;
        this.config = config;
    }

    @Override
    public License getLicense(String licenceId, String organizationId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenceId)
                .orElseThrow(() -> new IllegalArgumentException(String.format(
                        messages.getMessage("license.search.error.messaga", null, null),
                        licenceId,
                        organizationId
                )));
        Organization organization = retrieveOrganizationInfo(organizationId, clientType);
        if (null != organization) {
            license.setOrganizationName(organization.getName());
            license.setContactName(organization.getContactName());
            license.setContactEmail(organization.getContactEmail());
            license.setContactPhone(organization.getContactPhone());
        }
        return license.withComment(config.getProperty());
    }

    private Organization retrieveOrganizationInfo(String organizationId, String clientType) {
        Organization organization = null;
        switch (clientType) {
            case "feigh":
                System.out.println("Using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("Using rest client");
                organization = organizationRestTemplateClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("Using discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestTemplateClient.getOrganization(organizationId);
        }
        return organization;
    }

    @Override
    public License createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    @Override
    public License updateLicense(License license) {
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    @Override
    public String deleteLicense(String licenseId) {
        String responseMessage = null;
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        responseMessage = String.format(messages.getMessage(
                "license.delete.message", null, null), licenseId);
        return responseMessage;
    }

    public List<License> getLicenseByOrganization(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

}
