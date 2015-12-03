package com.ra.dissection.protocol.domain.protocol;

import com.ra.dissection.protocol.domain.common.Time;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 01.05.13 08:55
 */
public class DeathStoryEntry implements Serializable {

    private final long serialVersionUID = 201305010855000001l;

    private long id;

    /** Id of protocol to which this story entry belongs. */
    private long dissectionProtocolId;

    /** Name which can be edited */
    private String name;

    /** Source for name. It serves as source for original name. */
    private DeathStoryName sourceName;

    /** Date of entry occurence. */
    private Date date;

    /** Time of entry occurence. */
    private Time time = new Time();

    /** Optional description of entry. */
    private String description;

    public DeathStoryEntry() {
    }

    public DeathStoryEntry(DeathStoryName sourceName) {
        this.sourceName = sourceName;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeathStoryName getSourceName() {
        return sourceName;
    }

    public void setSourceName(DeathStoryName sourceName) {
        this.sourceName = sourceName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
