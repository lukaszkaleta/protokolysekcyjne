package com.ra.dissection.protocol.domain.protocol;

import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseName;

import java.io.Serializable;

/**
 * This object can be added to dissection diagnose.
 * It source is DissectionDiagnoseSourceOption.
 * It can be separately edited.
 *
 * @author lukaszkaleta
 * @since 18.07.13 10:46
 */
public class DissectionDiagnoseOption implements Serializable {

    private static final long serialVersionUID = 2013071810460000001l;

    /**
     * Unique id of this dissection diagnose option.
     */
    private long id;

    /**
     * Id of dissection diagnose where this option is added.
     */
    private long dissectionDiagnoseId;

    /**
     * Id of dissection diagnose source option.
     * DissectionDiagnoseSourceOption referenced here was a source for this object.
     */
    private long dissectionDiagnoseSourceOptionId;

    /**
     * Editable names.
     */
    private DissectionDiagnoseName name;

    /**
     * Sort index determine how values are displayed.
     */
    private int sortIndex;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDissectionDiagnoseId() {
        return dissectionDiagnoseId;
    }

    public void setDissectionDiagnoseId(long dissectionDiagnoseId) {
        this.dissectionDiagnoseId = dissectionDiagnoseId;
    }

    public long getDissectionDiagnoseSourceOptionId() {
        return dissectionDiagnoseSourceOptionId;
    }

    public void setDissectionDiagnoseSourceOptionId(long dissectionDiagnoseSourceOptionId) {
        this.dissectionDiagnoseSourceOptionId = dissectionDiagnoseSourceOptionId;
    }

    public DissectionDiagnoseName getName() {
        return name;
    }

    public void setName(DissectionDiagnoseName name) {
        this.name = name;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }
}
