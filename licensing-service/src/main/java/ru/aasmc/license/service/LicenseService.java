package ru.aasmc.license.service;

import ru.aasmc.license.model.License;

import java.util.List;
import java.util.Locale;

public interface LicenseService {
    License getLicense(String licenceId, String organizationId, String clientType);

    License createLicense(License license);

    License updateLicense(License license);

    String deleteLicense(String licenseId);

    List<License> getLicenseByOrganization(String organizationId);
}
