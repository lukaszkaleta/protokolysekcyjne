package com.ra.dissection.protocol.domain.protocol;

import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;

import java.util.Date;
import java.util.List;

/**
 * @author lukaszkaleta
 * @since 28.04.13 21:12, 6.8.3.0-R04v33
 */
public class DissectionProtocol {

    /** Id of dissection protocol. */
    private long id;

    private DissectionProtocolStatus status;

    private Date creationDate;

    /** Number of dissection protocol. */
    private String number;

    /** Category which will determine how other objects look like. */
    private DissectionProtocolCategory.Name category = DissectionProtocolCategory.Name.ADULT;

    /** Responsible hospital for dissection protocol. */
    private Long hospitalId;

    /** Basic data of dissection protocol. */
    private BasicData basicData = new BasicData();

    private String clinicalDiagnosis;

    private List<DescriptionPoint> descriptionPointList;

    private List<DissectionDiagnose> dissectionDiagnoseList;

    private List<HistopathologicalExamination> histopathologicalExaminations;

    private String clinicalData;

    private String medicalPracticeAnalysis;

    public DissectionProtocolStatus getStatus() {
        return status;
    }

    public void setStatus(DissectionProtocolStatus status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public DissectionProtocolCategory.Name getCategory() {
        return category;
    }

    public void setCategory(DissectionProtocolCategory.Name category) {
        this.category = category;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public BasicData getBasicData() {
        return basicData;
    }

    public void setBasicData(BasicData basicData) {
        this.basicData = basicData;
    }

    public String getClinicalDiagnosis() {
        return clinicalDiagnosis;
    }

    public void setClinicalDiagnosis(String clinicalDiagnosis) {
        this.clinicalDiagnosis = clinicalDiagnosis;
    }

    public List<DescriptionPoint> getDescriptionPointList() {
        return descriptionPointList;
    }

    public void setDescriptionPointList(List<DescriptionPoint> descriptionPointList) {
        this.descriptionPointList = descriptionPointList;
    }

    public List<DissectionDiagnose> getDissectionDiagnoseList() {
        return dissectionDiagnoseList;
    }

    public void setDissectionDiagnoseList(List<DissectionDiagnose> dissectionDiagnoseList) {
        this.dissectionDiagnoseList = dissectionDiagnoseList;
    }

    public List<HistopathologicalExamination> getHistopathologicalExaminations() {
        return histopathologicalExaminations;
    }

    public void setHistopathologicalExaminations(List<HistopathologicalExamination> histopathologicalExaminations) {
        this.histopathologicalExaminations = histopathologicalExaminations;
    }

    public String getClinicalData() {
        return clinicalData;
    }

    public void setClinicalData(String clinicalData) {
        this.clinicalData = clinicalData;
    }

    public String getMedicalPracticeAnalysis() {
        return medicalPracticeAnalysis;
    }

    public void setMedicalPracticeAnalysis(String medicalPracticeAnalysis) {
        this.medicalPracticeAnalysis = medicalPracticeAnalysis;
    }
}
