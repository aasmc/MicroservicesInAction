package ru.aasmc.license.service;

import ru.aasmc.license.model.License;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

public interface LicenseService {
    License getLicense(String licenceId, String organizationId, String clientType);

    License createLicense(License license);

    License updateLicense(License license);

    String deleteLicense(String licenseId);

    List<License> getLicenseByOrganization(String organizationId) throws TimeoutException;
}
