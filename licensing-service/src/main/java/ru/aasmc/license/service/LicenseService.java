package ru.aasmc.license.service;

import ru.aasmc.license.model.License;

import java.util.Locale;

public interface LicenseService {
    License getLicense(String licenceId, String organizationId);

    License createLicense(License license);

    License updateLicense(License license);

    String deleteLicense(String licenseId);
}
