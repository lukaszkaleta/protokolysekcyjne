package com.ra.dissection.protocol.service.impl;

import com.ra.dissection.protocol.domain.common.Patient;
import com.ra.dissection.protocol.util.AsciiString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lka on 12/15/16.
 */
public class ReportName {

    private static final Logger log = LoggerFactory.getLogger(ReportName.class);

    private static final String SPACE = " ";

    private final Patient patient;

    public ReportName(Patient patient) {
        this.patient = patient;
    }

    public String generate() {
        String reportName;

        String firstName = patient.getFirstName();
        String lastName = patient.getLastName();
        String description = patient.getDescription();

        try {
            String firstNameValue = extract(firstName);
            String lastNameValue = extract(lastName);
            String descriptionValue = extract(description);

            reportName = new AsciiString(firstNameValue + lastNameValue + descriptionValue).get();

            if (reportName == null || reportName.trim().isEmpty()) {
                reportName = patient.getIdentificationNumber();
            }

        } catch (Exception e) {
            log.error("REPORT NAME: [" + firstName + "][" + lastName + "] " + e.getMessage(), e);
            reportName = patient.getIdentificationNumber();
        }


        if (reportName == null || reportName.trim().isEmpty()) {
            reportName = "patientunknown";
        }

        return reportName;
    }

    private String extract(String value) {
        if (value == null) {
            return "";
        }
        if (value.trim().isEmpty()) {
            return "";
        }
        if (!value.contains(SPACE)) {
            return value;
        }
        return value.split(SPACE)[0];
    }
}
