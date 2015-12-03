package com.ra.dissection.protocol.domain.settings;

/**
 * @author lukaszkaleta
 * @since 01.05.13 08:34
 */
public class DissectionProtocolCategory {

    public enum Name {
        ADULT,
        NEWBORN,
        FETUS
    }

    private boolean adult = true;
    private boolean newborn = true;
    private boolean fetus = true;

    public DissectionProtocolCategory() {
    }

    public DissectionProtocolCategory(boolean adult, boolean newborn, boolean fetus) {
        this.adult = adult;
        this.newborn = newborn;
        this.fetus = fetus;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public boolean isNewborn() {
        return newborn;
    }

    public void setNewborn(boolean newborn) {
        this.newborn = newborn;
    }

    public boolean isFetus() {
        return fetus;
    }

    public void setFetus(boolean fetus) {
        this.fetus = fetus;
    }

    public boolean all() {
        return adult && fetus && newborn;
    }
}
