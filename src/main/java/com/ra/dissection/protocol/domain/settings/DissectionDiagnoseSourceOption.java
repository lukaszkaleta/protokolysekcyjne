package com.ra.dissection.protocol.domain.settings;

import java.io.Serializable;

/**
 * Some dissection diagnoses can have additional options (or sub points).
 * They can be defined as a list, but on protocol they should be selected.
 *
 * @author lukaszkaleta
 * @since 18.07.13 08:17
 */
public class DissectionDiagnoseSourceOption implements Serializable {

    private static final long serialVersionUID = 2013071808170000001l;

    /**
     * Unique id of this dissection diagnose source option.
     */
    private long id;

    /**
     * Id of dissection diagnose where this option belongs to.
     */
    private long dissectionDiagnoseSourceId;

    /**
     * Default name of an option.
     */
    private DissectionDiagnoseName name = new DissectionDiagnoseName();

    private int sortIndex;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDissectionDiagnoseSourceId() {
        return dissectionDiagnoseSourceId;
    }

    public void setDissectionDiagnoseSourceId(long dissectionDiagnoseSourceId) {
        this.dissectionDiagnoseSourceId = dissectionDiagnoseSourceId;
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
