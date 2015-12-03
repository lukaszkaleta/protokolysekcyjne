package com.ra.dissection.protocol.domain.protocol;

import com.ra.dissection.protocol.domain.common.Patient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class join basic data for dissection protocol.
 *
 * @author lukaszkaleta
 * @since 28.04.13 21:11
 */
public class BasicData implements Serializable {

    private static final long serialVersionUID = 2013042821110000001l;

    private Autopsy autopsy = new Autopsy();

    private DeathStory deathStory = new DeathStory();

    private Patient patient = new Patient();

    public Autopsy getAutopsy() {
        return autopsy;
    }

    public void setAutopsy(Autopsy autopsy) {
        this.autopsy = autopsy;
    }

    public DeathStory getDeathStory() {
        return deathStory;
    }

    public void setDeathStory(DeathStory deathStory) {
        this.deathStory = deathStory;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
