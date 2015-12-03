package com.ra.dissection.protocol.domain.settings;

import java.io.Serializable;

/**
 * Source of all dissection diagnoses.
 *
 * @author lukaszkaleta
 * @since 10.05.13 17:01
 */
public class DissectionDiagnoseSource implements Serializable {

    private static final long serialVersionUID = 13051017010000001l;

    /** Unique Id. */
    private long id;

    /** Id of description point which is associated with this diagnose. */
    private long descriptionPointSourceId;

    /** Flag which determine if description point is defined. */
    private boolean descriptionPointSourceAvailable;

    /** Flag which determine if dissection diagnose source options are defined. */
    private boolean dissectionDiagnoseSourceOptionAvailable;

    /** Name of the diagnose. */
    private DissectionDiagnoseName name = new DissectionDiagnoseName();

    /** Category to which this diagnose belongs. */
    private DissectionProtocolCategory category = new DissectionProtocolCategory();

    /** Type of dissection diagnose. */
    private DissectionDiagnoseType type = DissectionDiagnoseType.MACROSCOPIC;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDescriptionPointSourceId() {
        return descriptionPointSourceId;
    }

    public void setDescriptionPointSourceId(long descriptionPointSourceId) {
        this.descriptionPointSourceId = descriptionPointSourceId;
    }

    public boolean isDescriptionPointSourceAvailable() {
        return descriptionPointSourceAvailable;
    }

    public void setDescriptionPointSourceAvailable(boolean descriptionPointSourceAvailable) {
        this.descriptionPointSourceAvailable = descriptionPointSourceAvailable;
    }

    public boolean isDissectionDiagnoseSourceOptionAvailable() {
        return dissectionDiagnoseSourceOptionAvailable;
    }

    public void setDissectionDiagnoseSourceOptionAvailable(boolean dissectionDiagnoseSourceOptionAvailable) {
        this.dissectionDiagnoseSourceOptionAvailable = dissectionDiagnoseSourceOptionAvailable;
    }

    public DissectionDiagnoseName getName() {
        return name;
    }

    public void setName(DissectionDiagnoseName name) {
        this.name = name;
    }

    public DissectionProtocolCategory getCategory() {
        return category;
    }

    public void setCategory(DissectionProtocolCategory category) {
        this.category = category;
    }

    public DissectionDiagnoseType getType() {
        return type;
    }

    public void setType(DissectionDiagnoseType type) {
        this.type = type;
    }
}
