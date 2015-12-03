package com.ra.dissection.protocol.domain.protocol;

import java.io.Serializable;

/**
 * @author lukaszkaleta
 * @since 16.05.13 21:50
 */
public class HospitalWardEntry implements Serializable {

    private long id;

    private long hospitalWardId;

    private long dissectionProtocolId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHospitalWardId() {
        return hospitalWardId;
    }

    public void setHospitalWardId(long hospitalWardId) {
        this.hospitalWardId = hospitalWardId;
    }

    public long getDissectionProtocolId() {
        return dissectionProtocolId;
    }

    public void setDissectionProtocolId(long dissectionProtocolId) {
        this.dissectionProtocolId = dissectionProtocolId;
    }
}
