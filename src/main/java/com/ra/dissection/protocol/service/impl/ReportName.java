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
        String firstName = patient.getFirstName();
        String lastName = patient.getLastName();
        String description = patient.getDescription();

        try {
            String[] firstNamePart = firstName != null && firstName.contains(SPACE) ? firstName.split(SPACE) : new String[] {""};
            String[] lastNamePart = lastName != null && lastName.contains(SPACE) ? lastName.split(SPACE) : new String[] {""};
            String[] descriptionPart = description != null && description.contains(SPACE) ? description.split(SPACE) : new String[] {""};

            String part1 = firstNamePart[0];
            String part2 = lastNamePart[0];
            String part3 = descriptionPart[0];

            String reportName = new AsciiString(part1 + part2 + part3).get();

            if (reportName == null || reportName.trim().isEmpty()) {
                reportName = patient.getIdentificationNumber();
            }

            return reportName;
        } catch (Exception e) {
            log.error("REPORT NAME: [" + firstName + "][" + lastName + "] " + e.getMessage(), e);
            return patient.getIdentificationNumber();
        }
    }
}
