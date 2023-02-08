package ru.aasmc.license.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import ru.aasmc.license.model.License;
import ru.aasmc.license.service.LicenseService;

import java.util.Locale;
import java.util.Random;

@Service
public class LicenseServiceImpl implements LicenseService {
    private final MessageSource messages;

    @Autowired
    public LicenseServiceImpl(MessageSource messages) {
        this.messages = messages;
    }

    @Override
    public License getLicense(String licenceId, String organizationId) {
        License license = new License();
        license.setId(new Random().nextInt(1000));
        license.setLicenseId(licenceId);
        license.setOrganizationId(organizationId);
        license.setDescription("Software product");
        license.setProductName("Ostock");
        license.setLicenseType("full");
        return license;
    }

    @Override
    public String createLicense(License license, String organizationId, Locale locale) {
        String responseMessage = null;
        if (!ObjectUtils.isEmpty(license)) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format(
                    messages.getMessage("license.create.message", null, locale),
                    license.toString()
            );
        }
        return responseMessage;
    }

    @Override
    public String updateLicense(License license, String organizationId) {
        String responseMessage = null;
        if (!ObjectUtils.isEmpty(license)) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format(
                    messages.getMessage("license.update.message", null, null),
                    license.toString()
            );
        }
        return responseMessage;
    }

    @Override
    public String deleteLicense(String licenseId, String organizationId) {
        return String.format(
                messages.getMessage("license.delete.message", null, null),
                licenseId,
                organizationId
        );
    }
}
