package ru.aasmc.license.service;

import ru.aasmc.license.model.License;

import java.util.Locale;

public interface LicenseService {
    License getLicense(String licenceId, String organizationId);

    String createLicense(License license, String organizationId, Locale locale);

    String updateLicense(License license, String organizationId);

    String deleteLicense(String licenseId, String organizationId);
}
