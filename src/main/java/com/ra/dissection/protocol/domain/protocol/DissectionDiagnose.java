package com.ra.dissection.protocol.domain.protocol;

import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseName;
import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseSource;

import java.io.Serializable;

/**
 * Dissection diagnose for dissection protocol.
 * This object represent state of database.
 *
 * @author lukaszkaleta
 * @since 10.05.13 17:09
 */
public class DissectionDiagnose implements Serializable {

    private static final long serialVersionUID = 1305101709000001l;

    /** Unique id. */
    private long id;

    /** Id of protocol to which this dissection diagnose belongs. */
    private long dissectionProtocolId;

    /** Id of description point which contains detailed description for this diagnose. */
    private Long descriptionPointId;

    /** Id of diagnose source from which this dissection diagnose were created. */
    private long dissectionDiagnoseSourceId;

    /** Name of the diagnose. */
    private DissectionDiagnoseName name = new DissectionDiagnoseName();

    private int sortIndex;

    /** Each dissection diagnose can have space assigned. If space is present after dissection diagnose on the report we will have space. */
    private boolean space;

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

    public Long getDescriptionPointId() {
        return descriptionPointId;
    }

    public void setDescriptionPointId(Long descriptionPointId) {
        this.descriptionPointId = descriptionPointId;
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

    public boolean isSpace() {
        return space;
    }

    public void setSpace(boolean space) {
        this.space = space;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DissectionDiagnose that = (DissectionDiagnose) o;

        if (dissectionDiagnoseSourceId != that.dissectionDiagnoseSourceId) return false;
        if (dissectionProtocolId != that.dissectionProtocolId) return false;
        if (id != that.id) return false;
        if (sortIndex != that.sortIndex) return false;
        if (descriptionPointId != null ? !descriptionPointId.equals(that.descriptionPointId) : that.descriptionPointId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (dissectionProtocolId ^ (dissectionProtocolId >>> 32));
        result = 31 * result + (descriptionPointId != null ? descriptionPointId.hashCode() : 0);
        result = 31 * result + (int) (dissectionDiagnoseSourceId ^ (dissectionDiagnoseSourceId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + sortIndex;
        return result;
    }
}
