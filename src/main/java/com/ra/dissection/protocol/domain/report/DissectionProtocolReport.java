package com.ra.dissection.protocol.domain.report;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.ra.dissection.protocol.domain.common.Time;
import com.ra.dissection.protocol.domain.protocol.*;
import com.ra.dissection.protocol.domain.settings.*;
import com.ra.dissection.protocol.util.PeselValidator;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author lukaszkaleta
 * @since 13.05.13 22:43
 */
public abstract class DissectionProtocolReport {

    private final DissectionProtocolCategory.Name dissectionProtocolCategoryName;

    // Protocol data

    private Hospital hospital;

    private byte[] hospitalImage;

    private HospitalWard hospitalWard;

    private byte[] hospitalWardImage;

    private String number;

    // Basic Data

    private BasicData basicData;

    private final Map<DeathStoryName, DeathStoryEntry> deathStoryEntryMap = new LinkedHashMap<DeathStoryName, DeathStoryEntry>();

    private Doctor autopsyDoctor;

    private Hospital deathHospital;

    private List<HospitalWard> deathHospitalWards = new ArrayList<HospitalWard>();

    private PeselValidator peselValidator;

    // Clinical diagnosis

    private String clinicalDiagnosis;

    // Dissection diagnosis

    private Map<Long, DissectionDiagnose> dissectionDiagnoses;

    private Multimap<Long, DissectionDiagnoseOption> dissectionDiagnoseOptions;

    //
    // Description points
    //

    private Multimap<Integer,DescriptionPoint> descriptionPoints = LinkedHashMultimap.create();

    //
    // Histopathologica examination
    //

    private List<HistopathologicalExamination> histopathologicaExaminations;

    //
    // Clinical data
    //

    private String clinicalData;

    //
    // Medical Practice Analysis;
    //
    private String medicalPracticeAnalysis;

    public DissectionProtocolReport(DissectionProtocolCategory.Name dissectionProtocolCategoryName) {
        this.dissectionProtocolCategoryName = dissectionProtocolCategoryName;
    }

    public DissectionProtocolCategory.Name getDissectionProtocolCategoryName() {
        return dissectionProtocolCategoryName;
    }

    //
    // Protocol data installation
    //

    public void installProtocol(Hospital hospital, byte[] hospitalImage, HospitalWard hospitalWard, byte[] hospitalWardImage, String number) {
        this.hospital = hospital;
        this.hospitalImage = hospitalImage;
        this.hospitalWard = hospitalWard;
        this.hospitalWardImage = hospitalWardImage;
        this.number = number;
    }

    // Basic Data installation

    public void installBasicData(BasicData basicData, Doctor autopsyDoctor, Hospital deathHospital, List<HospitalWard> deathHospitalWards) {
        this.basicData = basicData;

        for (DeathStoryEntry deathStoryEntry : basicData.getDeathStory().getStoryEntries()) {

            if (deathStoryEntry.getDate() != null || deathStoryEntry.getTime().getValue() != null) {
                deathStoryEntryMap.put(deathStoryEntry.getSourceName(), deathStoryEntry);
            }
        }

        this.autopsyDoctor = autopsyDoctor == null ? new Doctor() : autopsyDoctor;
        this.deathHospital = deathHospital == null ? new Hospital() : deathHospital;

        this.deathHospitalWards.clear();
        if (deathHospitalWards != null) {
            this.deathHospitalWards.addAll(deathHospitalWards);
        }

        peselValidator = new PeselValidator(basicData.getPatient().getIdentificationNumber());
    }

    //
    // Clinical diagnosis installation

    public void installClinicalDiagnosis(String clinicalDiagnosis) {
        this.clinicalDiagnosis = cleanText(clinicalDiagnosis);
    }

    //
    // Install dissection diagnosis
    //

    public void installDissectionDiagnosis(Map<Long, DissectionDiagnose> dissectionDiagnoseMap, Multimap<Long, DissectionDiagnoseOption> dissectionDiagnoseOptions) {
        this.dissectionDiagnoses = dissectionDiagnoseMap;
        this.dissectionDiagnoseOptions = dissectionDiagnoseOptions;
    }

    //
    // Install description points
    //

    public void installDescriptionPoints(Multimap<Integer, DescriptionPoint> descriptionPoints) {
        this.descriptionPoints = descriptionPoints;
    }

    //
    // Install histopathological examination
    //

    public void installHistopathologicalExamination(List<HistopathologicalExamination> value) {
        this.histopathologicaExaminations = value;
    }

    //
    // Install basic data
    //

    public void installClinicalData(String value) {
        this.clinicalData = value;
    }

    //
    // Install medical practice analysis
    //

    public void installMedicalPracticeAnalysis(String value) {
        this.medicalPracticeAnalysis = value;
    }

    //
    // Protocol data reading
    //

    public Hospital getHospital() {
        return hospital;
    }

    public byte[] getHospitalImage() {
        return hospitalImage;
    }

    public HospitalWard getHospitalWard() {
        return hospitalWard;
    }

    public byte[] getHospitalWardImage() {
        return hospitalWardImage;
    }

    public String getNumber() {
        return number;
    }

    //
    // Basic date reading
    //

    public Date getDateFor(DeathStoryName deathStoryName) {
        DeathStoryEntry deathStoryEntry = deathStoryEntryMap.get(deathStoryName);
        if (deathStoryEntry != null) {
            return deathStoryEntry.getDate();
        } else {
            return null;
        }
    }

    public Time getTimeFor(DeathStoryName deathStoryName) {
        DeathStoryEntry deathStoryEntry = deathStoryEntryMap.get(deathStoryName);
        if (deathStoryEntry != null) {
            return deathStoryEntry.getTime();
        } else {
            return null;
        }
    }

    public String getHourFor(DeathStoryName deathStoryName) {
        Time time = getTimeFor(deathStoryName);
        return time.getHoursAsText();
    }

    public String getMinuteFor(DeathStoryName deathStoryName) {
        Time time = getTimeFor(deathStoryName);
        return time.getMinuteAsText();
    }

    public String getDescriptionFor(DeathStoryName deathStoryName) {
        DeathStoryEntry deathStoryEntry = deathStoryEntryMap.get(deathStoryName);
        return deathStoryEntry.getDescription();
    }

    public String getAutopsyHoursAfter(DeathStoryName deathStoryName) {
        Date autopsyDate = getBasicData().getAutopsy().getDate();
        if (autopsyDate == null) {
            return "";
        }
        Time autopsyTime = getBasicData().getAutopsy().getTime();
        if (autopsyTime == null || autopsyTime.getValue() == null) {
            return "";
        }

        Date storyDate = getDateFor(deathStoryName);
        if (storyDate == null) {
            return "";
        }
        Time storyTime = getTimeFor(deathStoryName);
        if (storyTime == null || storyTime.getValue() == null) {
            return "";
        }

        long autopsyMillisecondTime = autopsyDate.getTime() + autopsyTime.getMilliseconds();
        long storyMillisecondTime = storyDate.getTime() + storyTime.getMilliseconds();
        long hoursAfterDeath = TimeUnit.MILLISECONDS.toHours(autopsyMillisecondTime - storyMillisecondTime);
        return String.valueOf(hoursAfterDeath);
    }

    public String getIdentificationNumberYearAfter(DeathStoryName deathStoryName) {
        Date storyDate = getDateFor(deathStoryName);
        if (storyDate == null) {
            return "";
        }

        if (!peselValidator.isValid()) {
            return "";
        }
        int birthYear = peselValidator.getBirthYear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(storyDate);
        int storyYear = calendar.get(Calendar.YEAR);
        return String.valueOf(storyYear - birthYear);
    }

    public String getIdentificationNumberYearExactAfter(DeathStoryName deathStoryName) {
        Date storyDate = getDateFor(deathStoryName);
        if (storyDate == null) {
            return "";
        }

        if (!peselValidator.isValid()) {
            return "";
        }
        int birthYear = peselValidator.getBirthYear();
        int birthMonth = peselValidator.getBirthMonth();
        int birthDay = peselValidator.getBirthDay();
        GregorianCalendar gregorianCalendar = new GregorianCalendar(birthYear, birthMonth, birthDay);
        DateTime birthDateTime = new DateTime(gregorianCalendar.getTimeInMillis());
        DateTime storyDateTime = new DateTime(storyDate.getTime()).withMillisOfDay(0);
        Time timeFor = getTimeFor(deathStoryName);
        if (timeFor != null && timeFor.getValue() != null) {
            storyDateTime.plusHours(timeFor.getHour());
            storyDateTime.plusMinutes(timeFor.getMinute());
        }

        Period period = new Period(birthDateTime, storyDateTime);
        return String.valueOf(period.getYears());
    }

    public BasicData getBasicData() {
        return basicData;
    }

    public Doctor getAutopsyDoctor() {
        return autopsyDoctor;
    }

    public Hospital getDeathHospital() {
        return deathHospital;
    }

    public List<HospitalWard> getDeathHospitalWards() {
        return deathHospitalWards;
    }

    public Set<DeathStoryName> getDeathStoryNames() {
        return deathStoryEntryMap.keySet();
    }

    //
    // Clinical diagnosis reading
    //

    public String getClinicalDiagnosis() {
        return clinicalDiagnosis;
    }

    //
    // Dissection diagnosis reading
    //

    public Map<Long, DissectionDiagnose> getDissectionDiagnoses() {
        return dissectionDiagnoses;
    }

    public Collection<DissectionDiagnoseOption> getDissectionDiagnoseOptions(long dissectionDiagnoseId) {
        Collection<DissectionDiagnoseOption> options = dissectionDiagnoseOptions.get(dissectionDiagnoseId);
        if (options == null) {
            return Collections.emptySet();
        } else {
            return options;
        }
    }

    //
    // Description points reading
    //

    public Multimap<Integer,DescriptionPoint> getDescriptionPoints() {
        return descriptionPoints;
    }

    //
    // Histopathological examination reading
    //

    public List<HistopathologicalExamination> getHistopathologicaExaminations() {
        return histopathologicaExaminations;
    }

    //
    // Clinical data reading
    //

    public String getClinicalData() {
        return clinicalData;
    }

    //
    // Medial Practice Analysis
    //

    public String getMedicalPracticeAnalysis() {
        return medicalPracticeAnalysis;
    }


    //
    // UTILS
    //

    private String cleanText(String text) {
        if (text == null) {
            return null;
        }
        return text.replace("\r\n", "\r");
    }

    private String fullCleanText(String text) {
        if (text == null) {
            return null;
        }
        return text.replace("\r\n", "");
    }
}
