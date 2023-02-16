package ru.aasmc.license.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import ru.aasmc.license.config.ServiceConfig;
import ru.aasmc.license.model.License;
import ru.aasmc.license.repository.LicenseRepository;
import ru.aasmc.license.service.LicenseService;

import java.util.Locale;
import java.util.UUID;

@Service
public class LicenseServiceImpl implements LicenseService {

    private final MessageSource messages;
    private final LicenseRepository licenseRepository;
    private final ServiceConfig config;

    @Autowired
    public LicenseServiceImpl(MessageSource messages,
                              LicenseRepository licenseRepository,
                              ServiceConfig config) {
        this.messages = messages;
        this.licenseRepository = licenseRepository;
        this.config = config;
    }

    @Override
    public License getLicense(String licenceId, String organizationId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenceId)
                .orElseThrow(() -> new IllegalArgumentException(String.format(
                        messages.getMessage("license.search.error.messaga", null, null),
                        licenceId,
                        organizationId
                )));
        return license.withComment(config.getProperty());
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
        ;
        licenseRepository.delete(license);
        responseMessage = String.format(messages.getMessage(
                "license.delete.message", null, null), licenseId);
        return responseMessage;
    }

}
