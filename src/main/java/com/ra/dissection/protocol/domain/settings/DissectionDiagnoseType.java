package com.ra.dissection.protocol.domain.settings;

/**
 * @author lukaszkaleta
 * @since 18.07.13 07:37
 */
public enum DissectionDiagnoseType {

    MICROSCOPIC(false),
    MACROSCOPIC(true);

    private boolean descriptionRequired;

    DissectionDiagnoseType(boolean descriptionRequired) {
        this.descriptionRequired = descriptionRequired;
    }

    public boolean isDescriptionRequired() {
        return descriptionRequired;
    }
}
