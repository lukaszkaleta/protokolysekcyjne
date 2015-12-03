package com.ra.dissection.protocol.domain.search;

import com.ra.dissection.protocol.domain.common.Range;
import com.ra.dissection.protocol.domain.common.Time;

import java.util.Date;

/**
 * This class represents last user search.
 *
 * @author lukaszkaleta
 * @since 25.04.13 13:48
 */
public class UserSearch {

    /** Unique id of search. */
    private long id;

    /** Username which is owner of this search. */
    private String owner;

    /** Relation to doctor. */
    private Long doctorId;

    /** Relation to hospital. */
    private Long hospitalId;

    /** Strict date of medical examination. */
    private Date medicalExaminationDate;

    /** Range date of medical examination. */
    private Range<Date> medicalExaminationDateRange = new Range<Date>();

    /** Range time of medical examination. */
    private Range<Time> medicalExaminationTimeRange = new Range<Time>(new Time());

    /** Patient last name. */
    private String patientLastName;

    /** Patient first name. */
    private String patientFirstName;

    /** Patient identification number, it may be PESEL. */
    private String patientIdentificationNumber;

    /** Unique number of medical examination. */
    private String medicalExaminationNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Date getMedicalExaminationDate() {
        return medicalExaminationDate;
    }

    public void setMedicalExaminationDate(Date medicalExaminationDate) {
        this.medicalExaminationDate = medicalExaminationDate;
    }

    public Range<Date> getMedicalExaminationDateRange() {
        return medicalExaminationDateRange;
    }

    public void setMedicalExaminationDateRange(Range<Date> medicalExaminationDateRange) {
        this.medicalExaminationDateRange = medicalExaminationDateRange;
    }

    public Date getMedicalExaminationDateFrom() {
        return medicalExaminationDateRange.getFrom();
    }

    public void setMedicalExaminationDateFrom(Date from) {
        medicalExaminationDateRange.setFrom(from);
    }

    public Date getMedicalExaminationDateTo() {
        return medicalExaminationDateRange.getTo();
    }

    public void setMedicalExaminationDateTo(Date to) {
        medicalExaminationDateRange.setTo(to);
    }

    public Range<Time> getMedicalExaminationTimeRange() {
        return medicalExaminationTimeRange;
    }

    public void setMedicalExaminationTimeRange(Range<Time> medicalExaminationTimeRange) {
        this.medicalExaminationTimeRange = medicalExaminationTimeRange;
    }

    public Time getMedicalExaminationTimeFrom() {
        return medicalExaminationTimeRange.getFrom();
    }

    public void setMedicalExaminationTimeTo(Time to) {
        medicalExaminationTimeRange.setTo(to);
    }

    public Integer getMedicalExaminationTimeFromValue() {
        return medicalExaminationTimeRange.getFrom().getValue();
    }

    public void setMedicalExaminationTimeToValue(Integer to) {
        medicalExaminationTimeRange.setTo(new Time(to));
    }

    public Time getMedicalExaminationTimeTo() {
        return medicalExaminationTimeRange.getTo();
    }

    public void setMedicalExaminationTimeFrom(Time from) {
        medicalExaminationTimeRange.setFrom(from);
    }

    public Integer getMedicalExaminationTimeToValue() {
        return medicalExaminationTimeRange.getTo().getValue();
    }

    public void setMedicalExaminationTimeFromValue(Integer from) {
        medicalExaminationTimeRange.setFrom(new Time(from));
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientIdentificationNumber() {
        return patientIdentificationNumber;
    }

    public void setPatientIdentificationNumber(String patientIdentificationNumber) {
        this.patientIdentificationNumber = patientIdentificationNumber;
    }

    public String getMedicalExaminationNumber() {
        return medicalExaminationNumber;
    }

    public void setMedicalExaminationNumber(String medicalExaminationNumber) {
        this.medicalExaminationNumber = medicalExaminationNumber;
    }
}
