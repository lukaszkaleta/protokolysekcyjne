package com.ra.dissection.protocol.mvc.controller.protocol.support;

import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.protocol.HospitalWardEntry;
import com.ra.dissection.protocol.domain.settings.HospitalWard;
import com.ra.dissection.protocol.util.PatientDateCalculator;
import com.ra.dissection.protocol.util.PeselValidator;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 17.05.13 06:33
 */
public class BasicDataModel {

    public enum SubmitMode {
        SAVE, CATEGORY_SELECT, HOSPITAL_WARD_ADD, DEATH_HOSPITAL_SELECT
    }

    private SubmitMode submitMode;

    private DissectionProtocol dissectionProtocol;

    private List<NamedHospitalWard> namedHospitalWards = LazyList.decorate(new ArrayList(),
            FactoryUtils.instantiateFactory(NamedHospitalWard.class));

    private NamedHospitalWard newHospitalWard = new NamedHospitalWard();

    public SubmitMode getSubmitMode() {
        return submitMode;
    }

    public void setSubmitMode(SubmitMode submitMode) {
        this.submitMode = submitMode;
    }

    public DissectionProtocol getDissectionProtocol() {
        return dissectionProtocol;
    }

    public void setDissectionProtocol(DissectionProtocol dissectionProtocol) {
        this.dissectionProtocol = dissectionProtocol;
    }

    public List<NamedHospitalWard> getNamedHospitalWards() {
        return namedHospitalWards;
    }

    public void setNamedHospitalWards(List<NamedHospitalWard> namedHospitalWards) {
        this.namedHospitalWards = namedHospitalWards;
    }

    public NamedHospitalWard getNewHospitalWard() {
        return newHospitalWard;
    }

    public void setNewHospitalWard(NamedHospitalWard newHospitalWard) {
        this.newHospitalWard = newHospitalWard;
    }

    public static final class NamedHospitalWard {

        private HospitalWardEntry hospitalWardEntry = new HospitalWardEntry();

        private String name;

        public HospitalWardEntry getHospitalWardEntry() {
            return hospitalWardEntry;
        }

        public void setHospitalWardEntry(HospitalWardEntry hospitalWardEntry) {
            this.hospitalWardEntry = hospitalWardEntry;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    //
    // Prepare
    //

    public DissectionProtocol prepareForSubmit() {
        List<HospitalWardEntry> hospitalWardEntries = new ArrayList<HospitalWardEntry>();
        for (NamedHospitalWard namedHospitalWard : namedHospitalWards) {
            hospitalWardEntries.add(namedHospitalWard.getHospitalWardEntry());
        }
        hospitalWardEntries.addAll(dissectionProtocol.getBasicData().getDeathStory().getHospitalWardEntries());
        dissectionProtocol.getBasicData().getDeathStory().setHospitalWardEntries(hospitalWardEntries);

        int patientYearsAge = PatientDateCalculator.getPatientYearsAge(dissectionProtocol.getBasicData());
        if (patientYearsAge > 0) {
            dissectionProtocol.getBasicData().getPatient().setYearsAge(String.valueOf(patientYearsAge));
        }
        return dissectionProtocol;
    }

    public void prepareForView(DissectionProtocol dissectionProtocol, Map<Long, HospitalWard> hospitalWardMap) {
        namedHospitalWards.clear();

        this.dissectionProtocol = dissectionProtocol;
        List<HospitalWardEntry> hospitalWardEntries = dissectionProtocol.getBasicData().getDeathStory().getHospitalWardEntries();
        for (HospitalWardEntry hospitalWardEntry : hospitalWardEntries) {
            HospitalWard hospitalWard = hospitalWardMap.get(hospitalWardEntry.getHospitalWardId());
            if (hospitalWard != null) {
                NamedHospitalWard namedHospitalWard = new NamedHospitalWard();
                namedHospitalWard.setHospitalWardEntry(hospitalWardEntry);
                namedHospitalWard.setName(hospitalWard.getName());
                namedHospitalWards.add(namedHospitalWard);
            }
        }
    }
}
