package com.ra.dissection.protocol.domain.protocol;

import java.util.Date;

/**
 * @author lukaszkaleta
 * @since 21.05.13 17:04
 */
public class HistopathologicalExamination {

    public enum Name {
        NORMAL,
        DISSECTION_SECTOR,
        FETUS
    }

    private long id;

    private long dissectionProtocolId;

    private Name name;

    private String number;

    private Date fromDay;

    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDissectionProtocolId() {
        return dissectionProtocolId;
    }

    public void setDissectionProtocolId(long dissectionProtocolId) {
        this.dissectionProtocolId = dissectionProtocolId;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getFromDay() {
        return fromDay;
    }

    public void setFromDay(Date fromDay) {
        this.fromDay = fromDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
