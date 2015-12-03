package com.ra.dissection.protocol.domain.protocol;

/**
 * @author lukaszkaleta
 * @since 19.05.13 08:40
 */
public class DissectionProtocolProgress {

    private boolean basicDataDone;
    private boolean clinicalDiagnosisDone;
    private boolean dissectionDiagnosisDone;
    private boolean descriptionDone;
    private boolean histopathologicalExaminationDone;
    private boolean clinicalDataDone;
    private boolean medicalPracticeAnalysisDone;

    public boolean isBasicDataDone() {
        return basicDataDone;
    }

    public void setBasicDataDone(boolean basicDataDone) {
        this.basicDataDone = basicDataDone;
    }

    public boolean isClinicalDiagnosisDone() {
        return clinicalDiagnosisDone;
    }

    public void setClinicalDiagnosisDone(boolean clinicalDiagnosisDone) {
        this.clinicalDiagnosisDone = clinicalDiagnosisDone;
    }

    public boolean isDissectionDiagnosisDone() {
        return dissectionDiagnosisDone;
    }

    public void setDissectionDiagnosisDone(boolean dissectionDiagnosisDone) {
        this.dissectionDiagnosisDone = dissectionDiagnosisDone;
    }

    public boolean isDescriptionDone() {
        return descriptionDone;
    }

    public void setDescriptionDone(boolean descriptionDone) {
        this.descriptionDone = descriptionDone;
    }

    public boolean isHistopathologicalExaminationDone() {
        return histopathologicalExaminationDone;
    }

    public void setHistopathologicalExaminationDone(boolean histopathologicalExaminationDone) {
        this.histopathologicalExaminationDone = histopathologicalExaminationDone;
    }

    public boolean isClinicalDataDone() {
        return clinicalDataDone;
    }

    public void setClinicalDataDone(boolean clinicalDataDone) {
        this.clinicalDataDone = clinicalDataDone;
    }

    public boolean isMedicalPracticeAnalysisDone() {
        return medicalPracticeAnalysisDone;
    }

    public void setMedicalPracticeAnalysisDone(boolean medicalPracticeAnalysisDone) {
        this.medicalPracticeAnalysisDone = medicalPracticeAnalysisDone;
    }
}
