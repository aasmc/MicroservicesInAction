package ru.aasmc.license.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LicenseCollection {
    private List<License> licenses;

    public LicenseCollection(List<License> licenses) {
        this.licenses = licenses;
    }
}
